package org.example.mediqback.notification.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

public class NotificationDto {
    @Schema(description = "웹 푸시 알림 구독 요청 DTO")
    @Getter
    public static class Subscribe {
        @Schema(description = "푸시 엔드포인트 URL", example = "https://fcm.googleapis.com/fcm/send/...")
        private String endpoint;
        @Schema(description = "푸시 암호화 키 맵", example = "{\"p256dh\": \"...\", \"auth\": \"...\"}")
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

    @Schema(description = "웹 푸시 알림 전송 요청 DTO")
    @Getter
    @Builder
    public static class Send {
        @Schema(description = "알림 받을 사용자 번호", example = "1")
        private Long userIdx;
        @Schema(description = "알림 제목", example = "!! 곧 입장입니다 !!")
        private String title;
        @Schema(description = "알림 내용", example = "2번째 순서입니다. 진료를 위해 대기하여 주시기 바랍니다.")
        private String message;
    }

    @Schema(description = "알림 페이로드 DTO")
    @Getter
    @Builder
    public static class Payload {
        @Schema(description = "알림 제목", example = "!! 곧 입장입니다 !!")
        private String title;
        @Schema(description = "알림 내용", example = "2번째 순서입니다.")
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


    @Schema(description = "푸시 알림 검색 요청 DTO")
    @Getter
    @NoArgsConstructor
    public static class SearchReq {
        @Schema(description = "사용자 고유 번호", example = "1")
        private Long userIdx;
    }

    @Schema(description = "푸시 알림 검색 응답 DTO")
    @Getter
    @Builder
    @AllArgsConstructor
    public static class SearchRes {
        @Schema(description = "사용자 고유 번호", example = "1")
        private Long userIdx;
        @Schema(description = "푸시 인증키(auth)", example = "auth_key_string")
        private String auth;
        @Schema(description = "엔드포인트 URL", example = "https://fcm...")
        private String endpoint;
        @Schema(description = "p256dh 키", example = "p256dh_key_string")
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