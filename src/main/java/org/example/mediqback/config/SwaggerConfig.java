package org.example.mediqback.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // JWT 보안 체계 이름 설정
        String jwtSchemeName = "JWT";

        // API 요청 시 JWT 토큰을 요구하도록 설정
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        // JWT SecurityScheme (헤더에 Bearer 토큰을 담는 방식) 구성
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        // OpenAPI 객체 생성 및 반환
        return new OpenAPI()
                .info(new Info()
                        .title("BE24 스프링 API")
                        .description("API 명세서입니다.")
                        .version("1.0.0"))
                .addSecurityItem(securityRequirement) // 보안 요구사항 추가
                .components(components);              // 보안 컴포넌트 추가
    }
}