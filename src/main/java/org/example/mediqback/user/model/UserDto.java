package org.example.mediqback.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

public class UserDto {
    @Getter
    @Builder
    public static class OAuth {
        private String email;
        private String name;
        private String provider;
        private boolean enable;
        private String role;

        public static OAuth from(Map<String, Object> attributes, String provider) {
            String providerId = null;
            String email = null;
            Map properties = null;
            String name = null;

            if (provider.equals("kakao")) {
                providerId = ((Long) attributes.get("id")).toString();
                email = providerId + "@kakao.social";
                properties = (Map) attributes.get("properties");
                name = (String) properties.get("nickname");
            } else if(provider.equals("google")){
                email = (String)attributes.get("email");
                name = (String) attributes.get("name");
            }

            return OAuth.builder()
                    .email(email)
                    .name(name)
                    .provider(provider)
                    .enable(true)
                    .role("ROLE_USER")
                    .build();
        }

        public User toEntity() {
            return User.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(java.util.UUID.randomUUID().toString())
                    .enable(this.enable)
                    .role(this.role)
                    .provider(this.provider)
                    .build();
        }
    }
    @Schema(description = "회원가입 요청 DTO")
    @AllArgsConstructor
    @Getter
    public static class SignupReq {
        @Schema(description = "이메일 주소", example = "test@example.com")
        @Pattern(message = "이메일 형식이 아닙니다.", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        private String email;

        @Schema(description = "사용자 이름", example = "홍길동")
        private String name;

        @Schema(description = "비밀번호 (영문, 숫자, 특수문자 조합 8~20자)", example = "Password123!")
        @Pattern(message = "비밀번호는 숫자,영문,특수문자( !@#$%^&*() )를 조합해 8~20자로 생성해주세요.", regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*()])(?=.*[0-9]).{8,20}$")
        private String password;

        public User toEntity() {
            return User.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(this.password)
                    .enable(true)
                    .role("ROLE_USER")
                    .build();
        }
    }

    @Schema(description = "회원가입 응답 DTO")
    @Builder
    @Getter
    public static class SignupRes {

        @Schema(description = "회원 고유 번호", example = "1")
        private Long idx;

        @Schema(description = "이메일", example = "test@example.com")
        private String email;

        @Schema(description = "이름", example = "홍길동")
        private String name;

        public static SignupRes from(User entity) {
            return SignupRes.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .build();
        }
    }

    @Schema(description = "로그인 요청 DTO")
    @Getter
    public static class LoginReq {
        @Schema(description = "로그인 이메일", example = "test@example.com")
        private String email;
        @Schema(description = "로그인 비밀번호", example = "Password123!")
        private String password;
    }

    @Schema(description = "사용자 정보 응답 DTO")
    @Builder
    @Getter
    public static class LoginRes {
        @Schema(description = "회원 고유 번호", example = "1")
        private Long idx;
        @Schema(description = "이메일", example = "test@example.com")
        private String email;
        @Schema(description = "이름", example = "홍길동")
        private String name;
        @Schema(description = "사용자 권한", example = "ROLE_USER")
        private String role;

        public static LoginRes from(User entity) {
            return LoginRes.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .role(entity.getRole())
                    .build();
        }
    }
}