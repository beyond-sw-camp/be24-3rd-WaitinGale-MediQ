package org.example.mediqback.hospital.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HospitalDto {

    // 프론트엔드 -> 서버 (병원 정보 확인/저장 요청용)
    @Schema(description = "병원 확인 및 저장 요청 DTO")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Req {
        @Schema(description = "카카오맵 장소 ID", example = "12345678")
        private String kakaoPlaceId;
        @Schema(description = "병원명", example = "서울대학교병원")
        private String name;
        @Schema(description = "병원 주소", example = "서울 종로구 대학로 101")
        private String address;
        @Schema(description = "병원 전화번호", example = "02-1234-5678")
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
    @Schema(description = "병원 정보 응답 DTO")
    @Getter
    @Builder
    public static class Res {
        @Schema(description = "우리 DB의 병원 고유 번호", example = "1")
        private Long idx; // 우리 DB의 고유 번호
        @Schema(description = "카카오맵 장소 ID", example = "12345678")
        private String kakaoPlaceId;
        @Schema(description = "병원명", example = "서울대학교병원")
        private String name;
        @Schema(description = "병원 주소", example = "서울 종로구 대학로 101")
        private String address;
        @Schema(description = "병원 전화번호", example = "02-1234-5678")
        private String phone;
        @Schema(description = "예약금", example = "1")
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
    @Schema(description = "등록된 병원 검색 응답 DTO")
    @Getter
    @Builder
    public static class SearchRes {
        @Schema(description = "병원 고유 번호", example = "1")
        private Long idx;
        @Schema(description = "카카오맵 장소 ID", example = "12345678")
        private String kakaoPlaceId;
        @Schema(description = "병원명", example = "서울대학교병원")
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