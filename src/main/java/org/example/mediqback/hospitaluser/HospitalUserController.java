package org.example.mediqback.hospitaluser;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.hospitaluser.model.HospitalUserDto;
import org.example.mediqback.user.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/hospitaluser")
@RequiredArgsConstructor
public class HospitalUserController {

    private final HospitalUserService hospitalUserService;
    private final JwtUtil jwtUtil;

    // 병원 관계자 회원가입
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> signup(@RequestBody HospitalUserDto.SignupReq dto) {
        HospitalUserDto.Res response = hospitalUserService.signup(dto);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    // 병원 관계자 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody HospitalUserDto.LoginReq dto) {
        // 서비스에서 이메일/비밀번호 검증 후 유저 정보 받아오기
        HospitalUserDto.Res loginInfo = hospitalUserService.login(dto);

        // JWT 토큰 발급 (권한을 ROLE_HOSPITAL로 고정해서 발급)
        String jwt = jwtUtil.createToken(
                loginInfo.getIdx(),
                loginInfo.getEmail(),
                loginInfo.getName(),
                "ROLE_HOSPITAL"
        );

        // 기존 로그인과 완벽히 동일한 쿠키 생성
        ResponseCookie cookie = ResponseCookie.from("ATOKEN", jwt)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(BaseResponse.success(loginInfo));
    }
}