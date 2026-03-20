package org.example.mediqback.bookmark.model;

import lombok.*;
import org.example.mediqback.user.model.User;

public class BookmarkDto {
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reg {
        private String placeId;
        private String name;
        private String location;
        private Double latitude;
        private Double longitude;

        public Bookmark toEntity(User user) {
            return Bookmark.builder()
                    .placeId(this.placeId)
                    .name(this.name)
                    .location(this.location)
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
        private String placeId;
        private String name;
        private String location;

        private Double latitude;
        private Double longitude;

        public static Res from(Bookmark entity) {
            return Res.builder()
                    .idx(entity.getIdx())
                    .placeId(entity.getPlaceId())
                    .name(entity.getName())
                    .location(entity.getLocation())
                    .latitude(entity.getLatitude())
                    .longitude(entity.getLongitude())
                    .build();
        }

    }

}