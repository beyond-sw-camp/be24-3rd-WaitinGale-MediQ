package org.example.mediqback.finduserinfo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FindUserInfoDto {

    // 아이디 찾기 요청 (이름만 받음)
    @Getter @Setter @NoArgsConstructor
    public static class FindIdReq {
        private String name;
    }

    // 비밀번호 찾기 (이메일만 받음)
    @Getter @Setter @NoArgsConstructor
    public static class FindPwReq {
        private String email;
    }
}