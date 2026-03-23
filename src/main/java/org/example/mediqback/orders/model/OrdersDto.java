package org.example.mediqback.orders.model;

import lombok.Builder;
import lombok.Getter;

public class OrdersDto {
    @Builder
    @Getter
    public static class VerifyReq {
        private String paymentId;
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