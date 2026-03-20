package org.example.mediqback.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.user.model.AuthUserDetails;
import org.example.mediqback.user.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    // 로그인, 회원가입, 메일인증은 토큰 검사를 하지 않도록 설정.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        return path.startsWith("/user/login") ||
                path.startsWith("/user/signup") ||
                path.startsWith("/user/verify");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //요청에서 쿠키를 가져옵니다.
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                //쿠키 이름이 "ATOKEN"인 것을 찾는다.
                if (cookie.getName().equals("ATOKEN")) {
                    String token = cookie.getValue();

                    // JwtUtil을 통해 토큰에서 정보를 꺼냄.
                    Long idx = jwtUtil.getUserIdx(token);
                    String username = jwtUtil.getUsername(token);
                    String role = jwtUtil.getRole(token);

                    //시큐리티 전용 유저 객체(AuthUserDetails)를 만듬.
                    AuthUserDetails user = AuthUserDetails.builder()
                            .idx(idx)
                            .username(username)
                            .role(role)
                            .build();

                    //시큐리티 시스템에 "인증된 사용자"라고 등록.
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            List.of(new SimpleGrantedAuthority(role))
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // 다음 필터로 진행합니다.
        filterChain.doFilter(request, response);
    }
}