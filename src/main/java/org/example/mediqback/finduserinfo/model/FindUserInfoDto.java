package org.example.mediqback.finduserinfo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FindUserInfoDto {

    // 아이디 찾기 요청 (이름만 받음)
    @Schema(description = "아이디(이메일) 찾기 요청 DTO")
    @Getter @Setter @NoArgsConstructor
    public static class FindIdReq {
        @Schema(description = "가입 시 등록한 사용자 이름", example = "홍길동")
        private String name;
    }

    // 비밀번호 찾기 (이메일만 받음)
    @Schema(description = "임시 비밀번호 발급 요청 DTO")
    @Getter @Setter @NoArgsConstructor
    public static class FindPwReq {
        @Schema(description = "가입된 이메일 주소", example = "test@example.com")
        private String email;
    }
}