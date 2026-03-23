package org.example.mediqback.notification.model;

import lombok.*;
import org.example.mediqback.waiting.model.Waiting;

import java.util.Map;

public class NotificationDto {
    @Getter
    public static class Subscribe {
        private String endpoint;
        private Map<String, String> keys;
        @Setter
        private Long userIdx;

        public NotificationEntity toEntity() {
            return NotificationEntity.builder()
                    .endpoint(this.endpoint)
                    .userIdx(this.userIdx)
                    .p256dh(this.keys.get("p256dh"))
                    .auth(this.keys.get("auth"))
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Send {
        private Long userIdx;
        private String title;
        private String message;
    }

    @Getter
    @Builder
    public static class Payload {
        private String title;
        private String message;

        public static Payload from(Send dto) {
            return Payload.builder()
                    .title(dto.getTitle())
                    .message(dto.message)
                    .build();
        }

        @Override
        public String toString() {
            return "{\"title\":\""+this.title+"\", \"message\":\""+this.message+"\"}";
        }
    }


    @Getter
    @NoArgsConstructor
    public static class SearchReq {
        private Long userIdx;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SearchRes {
        private Long userIdx;
        private String auth;
        private String endpoint;
        private String p256dh;

        public static SearchRes from (NotificationEntity entity) {
            return SearchRes.builder()
                    .userIdx(entity.getUserIdx())
                    .auth(entity.getAuth())
                    .endpoint(entity.getEndpoint())
                    .p256dh(entity.getP256dh())
                    .build();
        }
    }


}
