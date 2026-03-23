package org.example.mediqback.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HospitalDto {

    // 프론트엔드 -> 서버 (병원 정보 확인/저장 요청용)
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Req {
        private String kakaoPlaceId;
        private String name;
        private String address;
        private String phone;

        // DTO를 DB에 저장할 Entity로 변환하는 메서드
        public Hospital toEntity() {
            return Hospital.builder()
                    .kakaoPlaceId(this.kakaoPlaceId)
                    .name(this.name)
                    .address(this.address)
                    .phone(this.phone)
                    .deposit(1) // 기본 예약금을 10,000원
                    .build();
        }
    }

    // 서버 -> 프론트엔드 (DB에 저장된 우리 병원 정보 응답용)
    @Getter
    @Builder
    public static class Res {
        private Long idx; // 우리 DB의 고유 번호
        private String kakaoPlaceId;
        private String name;
        private String address;
        private String phone;
        private int deposit; // 예약금

        // Entity를 DTO로 변환하는 메서드
        public static Res from(Hospital entity) {
            return Res.builder()
                    .idx(entity.getIdx())
                    .kakaoPlaceId(entity.getKakaoPlaceId())
                    .name(entity.getName())
                    .address(entity.getAddress())
                    .phone(entity.getPhone())
                    .deposit(entity.getDeposit())
                    .build();
        }
    }

    // 대기 뜨는 병원만 검색하는 기능
    @Getter
    @Builder
    public static class SearchRes {
        private Long idx;
        private String kakaoPlaceId;
        private String name;

        public static SearchRes from(Hospital hospital) {
            return SearchRes.builder()
                    .idx(hospital.getIdx())
                    .kakaoPlaceId(hospital.getKakaoPlaceId())
                    .name(hospital.getName())
                    .build();
        }
    }
}