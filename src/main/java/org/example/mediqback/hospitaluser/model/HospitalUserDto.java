package org.example.mediqback.hospitaluser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.mediqback.hospital.model.Hospital;

public class HospitalUserDto {

    // 회원가입 요청 DTO
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SignupReq {
        private String email;
        private String password;
        private String name;
        private String kakaoPlaceId;

        // 컨트롤러/서비스에서 찾은 Hospital 객체를 여기에 넣어 호출합니다.
        public HospitalUser toEntity(Hospital hospital) {
            return HospitalUser.builder()
                    .email(this.email)
                    .password(this.password)
                    .name(this.name)
                    .hospital(hospital) // 여기서 병원(건물)과 사람을 연결
                    .role("ROLE_HOSPITAL") // 병원 권한 강제 부여
                    .build();
        }
    }

    // 로그인 요청 DTO
    @Getter
    @NoArgsConstructor
    public static class LoginReq {
        private String email;
        private String password;
    }

    // 응답 DTO
    @Getter
    @Builder
    public static class Res {
        private Long idx;
        private String email;
        private String name;
        private String hospitalName;
        private String role;

        public static Res from(HospitalUser entity) {
            return Res.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .hospitalName(entity.getHospital() != null ? entity.getHospital().getName() : null)
                    .role(entity.getRole())
                    .build();
        }
    }
}