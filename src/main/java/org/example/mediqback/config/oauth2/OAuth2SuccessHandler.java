package org.example.mediqback.config.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mediqback.user.model.AuthUserDetails;
import org.example.mediqback.user.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthUserDetails user = (AuthUserDetails) authentication.getPrincipal();

        // JWT 토큰 생성
        String jwt = jwtUtil.createToken(
                user.getIdx(),
                user.getUsername(),
                user.getName(),
                user.getRole()
        );

        // 쿠키 대신, 프론트엔드 주소(?token=)에 토큰을 직접 달아서 보냅니다.
        String redirectUrl = "http://localhost:5173/?token=" + jwt;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}