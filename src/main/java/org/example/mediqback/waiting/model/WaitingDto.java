package org.example.mediqback.waiting.model;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

public class WaitingDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WaitingReq {
        private Long userIdx;
        private Long hospitalIdx;
        private int waitingNumber;
        private Status status;

        @Column
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Waiting toEntity(WaitingDto.WaitingReq dto) {
            return Waiting.builder()
                    .userIdx(dto.getUserIdx())
                    .hospitalIdx(dto.getHospitalIdx())
                    .waitingNumber(dto.getWaitingNumber())
                    .status(Status.WAITING)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class WaitingRes {
        private Long userIdx;
        private Long hospitalIdx;
        private Status status;
        private LocalDateTime createdAt;

        public static WaitingRes from(Waiting entity) {
            return WaitingRes.builder()
                    .userIdx(entity.getUserIdx())
                    .hospitalIdx(entity.getHospitalIdx())
                    .status(entity.getStatus())
                    .build();
        }
    }

    @Builder
    public static class MyOrder {
        private Long UserIdx;
        private Long hospitalIdx;
        private int waitingNumber;

        public static Waiting from(Waiting entity) {
            return Waiting.builder()
                    .waitingNumber(entity.getWaitingNumber())
                    .build();
        }
    }

}
