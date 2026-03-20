package org.example.mediqback.notification.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    public static class Send {
        private Long idx;
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
}
