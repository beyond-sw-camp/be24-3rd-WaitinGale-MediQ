package org.example.mediqback.queue.model;

import lombok.*;

public class QueueDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QueueReq {
        private Long hospitalIdx;
        private int currentNo;
        private int lastNo;

        public Queue toEntity(QueueReq dto) {
            return Queue.builder()
                    .hospitalIdx(dto.getHospitalIdx())
                    .currentNo(dto.getCurrentNo())
                    .lastNo(dto.getLastNo())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class QueueRes {
        private Long hospitalIdx;
        private int currentNo;
        private int lastNo;

        public static QueueRes from (Queue entity) {
            return QueueRes.builder()
                    .hospitalIdx(entity.getHospitalIdx())
                    .currentNo(entity.getCurrentNo())
                    .lastNo(entity.getLastNo())
                    .build();
        }
    }
}
