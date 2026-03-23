package org.example.mediqback.user;

import org.example.mediqback.common.model.BaseResponseStatus;
import org.example.mediqback.user.model.AuthUserDetails;
import org.example.mediqback.user.model.UserDto;
import org.example.mediqback.user.utils.JwtUtil;
import org.example.mediqback.common.model.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@CrossOrigin
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody UserDto.SignupReq dto) {
        UserDto.SignupRes result =  userService.signup(dto);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

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

    @GetMapping("/verify")
    public ResponseEntity verify(String uuid) {
        userService.verify(uuid);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create("http://localhost:5173")).build();
    }
}