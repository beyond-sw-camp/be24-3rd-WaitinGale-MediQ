package org.example.mediqback.config.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mediqback.user.model.AuthUserDetails;
import org.example.mediqback.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Value("${app.frontend-url}")
    private String frontendUrl;

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

        //  일반 로그인과 완벽하게 동일한 HttpOnly 안전한 쿠키 생성
        ResponseCookie cookie = ResponseCookie.from("ATOKEN", jwt)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        //  헤더에 쿠키 담기
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        //  URL에 토큰을 덕지덕지 붙이지 않고, 깔끔하게 메인 페이지로만 리다이렉트!
        getRedirectStrategy().sendRedirect(request, response, frontendUrl);
    }
}