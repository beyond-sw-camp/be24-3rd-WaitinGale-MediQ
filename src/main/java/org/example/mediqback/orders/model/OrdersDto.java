package org.example.mediqback.orders.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrdersDto {

    // 기존 VerifyReq와 테스트용으로 추가한 필드를 하나로 합쳤습니다.
    @Schema(description = "결제 검증 요청 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifyReq {
        @Schema(description = "포트원 결제 고유 ID", example = "imp_1234567890")
        private String paymentId;

        @Schema(description = "주문 번호 (성능 테스트 시나리오용)", example = "1")
        private Long ordersIdx; // 성능 테스트 시나리오를 위해 추가!
    }

    @Schema(description = "예약 주문 생성 요청 DTO")
    @Builder
    @Getter
    public static class OrdersReq {
        // 병원 번호 하나만 받기
        @Schema(description = "예약할 병원의 고유 번호", example = "1")
        private Long hospitalIdx;

        @Schema(description = "예약 방문 날짜", example = "2026-03-25")
        private String reservationDate;

        @Schema(description = "예약 방문 시간", example = "14:30")
        private String reservationTime;
    }

    @Schema(description = "예약 주문 생성 응답 DTO")
    @Builder
    @Getter
    public static class OrdersRes {
        @Schema(description = "생성된 주문 고유 번호", example = "1")
        private Long ordersIdx;

        @Schema(description = "결제 완료 여부", example = "false")
        private boolean paid;

        public static OrdersRes from(Orders entity) {
            return OrdersRes.builder()
                    .ordersIdx(entity.getIdx())
                    .paid(entity.isPaid())
                    .build();
        }
    }
}