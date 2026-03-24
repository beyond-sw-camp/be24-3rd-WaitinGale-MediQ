package org.example.mediqback.queue.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class QueueDto {
    @Schema(description = "대기열 등록 요청 DTO")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QueueReq {
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "현재 호출 번호", example = "0")
        private int currentNo;
        @Schema(description = "마지막 발급 번호", example = "1")
        private int lastNo;

        public Queue toEntity(QueueReq dto) {
            return Queue.builder()
                    .hospitalIdx(dto.getHospitalIdx())
                    .currentNo(dto.getCurrentNo())
                    .lastNo(dto.getLastNo())
                    .build();
        }
    }

    @Schema(description = "대기열 응답 DTO")
    @Builder
    @Getter
    public static class QueueRes {
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "현재 호출 번호", example = "0")
        private int currentNo;
        @Schema(description = "마지막 발급 번호", example = "1")
        private int lastNo;

        public static QueueRes from (Queue entity) {
            return QueueRes.builder()
                    .hospitalIdx(entity.getHospitalIdx())
                    .currentNo(entity.getCurrentNo())
                    .lastNo(entity.getLastNo())
                    .build();
        }
    }

    @Schema(description = "다음 순서 호출 요청 DTO")
    @Builder
    @Getter
    public static class NextReq {
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "현재 호출 번호", example = "0")
        private int currentNo;
        @Schema(description = "마지막 발급 번호", example = "1")
        private int lastNo;

        public Queue toEntity(NextReq dto) {
            return Queue.builder()
                    .hospitalIdx(dto.getHospitalIdx())
                    .currentNo(dto.getCurrentNo())
                    .lastNo(dto.getLastNo())
                    .build();
        }
    }

    @Schema(description = "다음 순서 호출 응답 DTO")
    @Builder
    @Getter
    public static class NextRes {
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "현재 호출 번호", example = "1")
        private int currentNo;
        @Schema(description = "마지막 발급 번호", example = "1")
        private int lastNo;

        public NextRes from(Queue queue) {
            return NextRes.builder()
                    .hospitalIdx(queue.getHospitalIdx())
                    .currentNo(queue.getCurrentNo())
                    .lastNo(queue.getLastNo())
                    .build();
        }
    }
}