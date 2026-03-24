package org.example.mediqback.orders.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrdersDto {

    // 기존 VerifyReq와 테스트용으로 추가한 필드를 하나로 합쳤습니다.
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifyReq {
        private String paymentId;
        private Long ordersIdx; // 성능 테스트 시나리오를 위해 추가!
    }

    @Builder
    @Getter
    public static class OrdersReq {
        // 병원 번호 하나만 받기
        private Long hospitalIdx;
        private String reservationDate;
        private String reservationTime;
    }

    @Builder
    @Getter
    public static class OrdersRes {
        private Long ordersIdx;
        private boolean paid;

        public static OrdersRes from(Orders entity) {
            return OrdersRes.builder()
                    .ordersIdx(entity.getIdx())
                    .paid(entity.isPaid())
                    .build();
        }
    }
}