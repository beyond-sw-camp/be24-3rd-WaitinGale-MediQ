package org.example.mediqback.hospital.model;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.processing.Pattern;

import javax.swing.text.html.parser.Entity;

public class HospitalDto {

    @Getter
    public static class SignupReq {
        private String email;
        private String name;
        private String password;
        private String location;
        private String tell;

        public Hospital toEntity() {
            return Hospital.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(this.password)
                    .location(this.location)
                    .tell(this.tell)
                    .enable(false)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SignupRes {
        private Long idx;
        private String email;
        private String name;
        private String location;
        private String tell;

        public static SignupRes from(Hospital entity) {
            return SignupRes.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .location(entity.getLocation())
                    .tell(entity.getTell())
                    .build();
        }

    }

    @Getter
    public static class LoginReq {
        private String email;
        private String password;
    }

    @Builder
    @Getter
    public static class LoginRes {
        private Long idx;
        private String name;
        private String email;

        public static LoginRes from(Hospital entity) {
            return LoginRes.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .build();
        }
    }


    // 대기 뜨는 병원만 검색하는 기능
    @Getter
    @Builder
    public static class SearchRes {
        private Long idx;
        private String placeId;
        private String name;

        public static SearchRes from(Hospital hospital) {
            return SearchRes.builder()
                    .idx(hospital.getIdx())
                    .placeId(hospital.getPlaceId())
                    .name(hospital.getName())
                    .build();
        }
    }
}
