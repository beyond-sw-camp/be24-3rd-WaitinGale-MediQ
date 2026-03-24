package org.example.mediqback.paymenthistory.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class PaymentHistoryDto {

    @Schema(description = "결제 내역 응답 DTO")
    @Getter
    @Builder
    public static class Response {
        @Schema(description = "결제 내역 고유 번호", example = "1")
        private Long historyId;

        @Schema(description = "결제 항목 이름", example = "OO병원 예약금")
        private String paymentName;

        @Schema(description = "결제 금액", example = "10000")
        private Long amount;

        @Schema(description = "결제 수단", example = "앱결제")
        private String paymentMethod;

        @Schema(description = "결제 상태", example = "결제완료")
        private String paymentStatus;

        @Schema(description = "결제 일시", example = "2026-03-24 14:30:00")
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