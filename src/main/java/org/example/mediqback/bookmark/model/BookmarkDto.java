package org.example.mediqback.bookmark.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.example.mediqback.user.model.User;
import org.example.mediqback.bookmark.model.Bookmark;

public class BookmarkDto {
    @Schema(description = "북마크 등록 요청 DTO")
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reg {
        @Schema(description = "카카오맵 장소 ID", example = "12345678")
        private String kakaoPlaceId;
        @Schema(description = "장소명", example = "서울대학교병원")
        private String name;
        @Schema(description = "주소", example = "서울 종로구 대학로 101")
        private String address;
        @Schema(description = "위도", example = "37.5796")
        private Double latitude;
        @Schema(description = "경도", example = "126.9996")
        private Double longitude;

        public Bookmark toEntity(User user) {
            return Bookmark.builder()
                    .kakaoPlaceId(this.kakaoPlaceId)
                    .name(this.name)
                    .address(this.address)
                    .latitude(this.latitude)
                    .longitude(this.longitude)
                    .user(user)
                    .build();
        }
    }

    @Schema(description = "북마크 응답 DTO")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Res {
        @Schema(description = "북마크 고유 번호", example = "1")
        private Long idx;
        @Schema(description = "카카오맵 장소 ID", example = "12345678")
        private String kakaoPlaceId;
        @Schema(description = "장소명", example = "서울대학교병원")
        private String name;
        @Schema(description = "주소", example = "서울 종로구 대학로 101")
        private String address;

        @Schema(description = "위도", example = "37.5796")
        private Double latitude;
        @Schema(description = "경도", example = "126.9996")
        private Double longitude;

        public static Res from(Bookmark entity) {
            return Res.builder()
                    .idx(entity.getIdx())
                    .kakaoPlaceId(entity.getKakaoPlaceId())
                    .name(entity.getName())
                    .address(entity.getAddress())
                    .latitude(entity.getLatitude())
                    .longitude(entity.getLongitude())
                    .build();
        }

    }

}