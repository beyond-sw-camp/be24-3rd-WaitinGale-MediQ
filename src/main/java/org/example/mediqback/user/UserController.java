package org.example.mediqback.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.user.model.AuthUserDetails;
import org.example.mediqback.user.model.UserDto;
import org.example.mediqback.user.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

            return ResponseEntity.ok()
                    .header("Set-Cookie", "ATOKEN=" + jwt + "; Path=/")
                    .body(loginInfo);
        }

        // Demo와 동일한 응답 처리
        return ResponseEntity.ok("로그인 실패");
    }

    @GetMapping("/verify")
    public ResponseEntity verify(String uuid) {
        userService.verify(uuid);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create("http://localhost:5173")).build();
    }
}