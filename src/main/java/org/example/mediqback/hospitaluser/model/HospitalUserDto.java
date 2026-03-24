package org.example.mediqback.hospitaluser.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.mediqback.hospital.model.Hospital;

public class HospitalUserDto {

    // 회원가입 요청 DTO
    @Schema(description = "병원 관계자 회원가입 요청 DTO")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SignupReq {
        @Schema(description = "이메일", example = "hospital@example.com")
        private String email;
        @Schema(description = "비밀번호", example = "Password123!")
        private String password;
        @Schema(description = "담당자 이름", example = "김원장")
        private String name;
        @Schema(description = "소속 병원의 카카오맵 장소 ID", example = "12345678")
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
    @Schema(description = "병원 관계자 로그인 요청 DTO")
    @Getter
    @NoArgsConstructor
    public static class LoginReq {
        @Schema(description = "이메일", example = "hospital@example.com")
        private String email;
        @Schema(description = "비밀번호", example = "Password123!")
        private String password;
    }

    // 응답 DTO
    @Schema(description = "병원 관계자 정보 응답 DTO")
    @Getter
    @Builder
    public static class Res {
        @Schema(description = "계정 고유 번호", example = "1")
        private Long idx;
        @Schema(description = "이메일", example = "hospital@example.com")
        private String email;
        @Schema(description = "담당자 이름", example = "김원장")
        private String name;
        @Schema(description = "소속 병원명", example = "서울대학교병원")
        private String hospitalName;
        @Schema(description = "권한", example = "ROLE_HOSPITAL")
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