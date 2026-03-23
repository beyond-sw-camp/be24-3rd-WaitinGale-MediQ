package org.example.mediqback.bookmark.model;

import lombok.*;
import org.example.mediqback.user.model.User;
import org.example.mediqback.Bookmark.model.Bookmark;

public class BookmarkDto {
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reg {
        private String kakaoPlaceId;
        private String name;
        private String address;
        private Double latitude;
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
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Res {
        private Long idx;
        private String kakaoPlaceId;
        private String name;
        private String address;

        private Double latitude;
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