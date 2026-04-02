package org.example.mediqback.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.config.filter.JwtFilter;
import org.example.mediqback.config.oauth2.OAuth2AuthorizationRequestRepository;
import org.example.mediqback.config.oauth2.OAuth2SuccessHandler;
import org.example.mediqback.user.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

    @Value("${app.cors.allowed-origins}")
    private List<String> allowedOrigins;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.oauth2Login(config -> {
            config.authorizationEndpoint(endpoint ->
                    endpoint.authorizationRequestRepository(oAuth2AuthorizationRequestRepository)
            );
            config.userInfoEndpoint(endpoint ->
                    endpoint.userService(customOAuth2UserService)
            );
            config.successHandler(oAuth2SuccessHandler);
        });

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.authorizeHttpRequests((auth) -> auth
                // 누구나 접근 가능한 곳
                .requestMatchers
                        (
                                "/user/login", "/user/signup", "/user/verify", "/hospitaluser/login", "/hospitaluser/signup"
                        ).permitAll()
                // 로그인이 꼭 필요한 곳
                .requestMatchers("/mypage/**").authenticated()
                // 그 외 나머지 주소는 일단 전부 열어두기
                .anyRequest().permitAll()
        );

        http.exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((request, response, authException) -> {
                    // 토큰이 없거나 인증되지 않은 사용자가 접근할 때 프론트엔드에 401 상태 코드와 JSON 응답을 보냅니다.
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드

                    // BaseResponse 형식에 맞춘 에러 메시지 작성
                    String jsonResponse = "{\"success\":false,\"code\":3002,\"message\":\"인증이 필요합니다. 다시 로그인해주세요.\",\"result\":null}";
                    response.getWriter().write(jsonResponse);
                })
        );

        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Demo의 PasswordEncoderFactories 방식 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}