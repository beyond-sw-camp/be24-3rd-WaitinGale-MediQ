package org.example.mediqback.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.common.model.BaseResponseStatus;
import org.example.mediqback.user.model.AuthUserDetails;
import org.example.mediqback.user.model.UserDto;
import org.example.mediqback.user.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "User API", description = "일반 사용자 회원가입, 로그인 및 프로필 관리 API")
@CrossOrigin
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Operation(summary = "일반 사용자 회원가입", description = "새로운 일반 사용자를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패 또는 중복된 이메일")
    })
    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody UserDto.SignupReq dto) {
        UserDto.SignupRes result =  userService.signup(dto);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Operation(summary = "일반 사용자 로그인", description = "이메일과 비밀번호로 로그인하고, 인증 쿠키(ATOKEN)를 발급받습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공 (쿠키 발급)"),
            @ApiResponse(responseCode = "400", description = "이메일이나 비밀번호 오류")
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto.LoginReq dto) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(), null);

        Authentication authentication = authenticationManager.authenticate(token);
        AuthUserDetails user = (AuthUserDetails) authentication.getPrincipal();

        if(user != null) {
            String jwt = jwtUtil.createToken(user.getIdx(), user.getUsername(), user.getName(), user.getRole());

            UserDto.LoginRes loginInfo = UserDto.LoginRes.builder()
                    .idx(user.getIdx())
                    .email(user.getUsername())
                    .name(user.getName())
                    .build();

            // 여기서 안전한 쿠키(ResponseCookie)를 생성합니다.
            ResponseCookie cookie = ResponseCookie.from("ATOKEN", jwt)
                    .path("/")
                    .httpOnly(true)    // 자바스크립트(document.cookie)로 접근 절대 불가 (XSS 방어)
                    .secure(true)      // HTTPS 통신에서만 전송
                    .sameSite("None")  // 프론트(5173)와 백엔드(8080) 포트가 다를 때도 쿠키가 전송되도록 허용
                    //.maxAge(60 * 30) // 원한다면 쿠키 자체의 수명도 초 단위로 설정 가능 (예: 30분)
                    .build();

            return ResponseEntity.ok()
                    // 헤더에 생성한 안전한 쿠키를 담아서 보냅니다.
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(loginInfo);
        }

        return ResponseEntity.ok("로그인 실패");
    }

    @Operation(summary = "내 프로필 조회", description = "현재 로그인된 사용자의 프로필 정보를 조회합니다. (JWT 쿠키 필요)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 JWT 토큰")
    })
    @GetMapping("/profile")
    public ResponseEntity<BaseResponse> getProfile(@AuthenticationPrincipal AuthUserDetails user) {
        // JwtFilter에서 토큰 검증을 통과하지 못하면 user는 null이 됩니다.
        if (user == null) {
            return ResponseEntity.status(401).body(BaseResponse.fail(BaseResponseStatus.JWT_INVALID));
        }

        // 토큰이 유효하면 유저 정보를 프론트에 넘겨줍니다.
        UserDto.LoginRes userInfo = UserDto.LoginRes.builder()
                .idx(user.getIdx())
                .email(user.getUsername())
                .name(user.getName())
                .build();

        return ResponseEntity.ok(BaseResponse.success(userInfo));
    }

    @Operation(summary = "로그아웃", description = "사용자의 로그인 토큰 쿠키를 삭제하여 로그아웃 처리합니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    @PostMapping("/logout")
    public ResponseEntity logout() {
        // 내용물을 비우고, 수명(maxAge)을 0으로 만든 빈 쿠키를 생성.
        ResponseCookie cookie = ResponseCookie.from("ATOKEN", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0) //브라우저에게  쿠키를 삭제하게하는 0으로 만드는 기능.
                .build();

        return ResponseEntity.ok()
                // 헤더에 빈 쿠키를 담아서 덮어쓰기.
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(BaseResponse.success("로그아웃 성공"));
    }

    @Operation(summary = "이메일 인증 확인", description = "이메일로 발송된 UUID 링크를 통해 계정을 활성화합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "301", description = "인증 성공 후 프론트엔드로 리다이렉트"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인증키")
    })
    @GetMapping("/verify")
    public ResponseEntity verify(
            @Parameter(description = "이메일로 전송받은 고유 인증키(UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
            String uuid) {
        userService.verify(uuid);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create("http://localhost:5173")).build();
    }
}