package org.example.mediqback.paymenthistory.model;


import lombok.Builder;
import lombok.Getter;

public class PaymentHistoryDto {

    @Getter
    @Builder
    public static class Response {
        private Long historyId;
        private String paymentName;
        private Long amount;
        private String paymentMethod;
        private String paymentStatus;
        private String paymentDate;

        public static Response from(PaymentHistory entity) {
            return Response.builder()
                    .historyId(entity.getIdx())
                    .paymentName(entity.getPaymentName())
                    .amount(entity.getAmount())
                    .paymentMethod(entity.getPaymentMethod())
                    .paymentStatus(entity.getPaymentStatus())
                    .paymentDate(entity.getCreatedAt().toString()) // 필요 시 SimpleDateFormat 등으로 형태 변환
                    .build();
        }
    }
}