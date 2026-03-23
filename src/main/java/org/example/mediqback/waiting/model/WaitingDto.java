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
        private Long hospitalIdx;
        private Long userIdx;
        private int waitingNumber;

        @Column
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Waiting toEntity(WaitingReq dto) {
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

    @Builder
    @Getter
    public static class ListRes {
        private Long hospitalIdx;
        private Long userIdx;
        private int waitingNumber;
        private Status status;

        // Entity > Dto
        public static ListRes from(Waiting entity) {
            return ListRes.builder()
                    .hospitalIdx(entity.getHospitalIdx())
                    .userIdx(entity.getUserIdx())
                    .waitingNumber(entity.getWaitingNumber())
                    .status(entity.getStatus())
                    .build();
        }
    }


    @Getter
    @Builder
    public static class DeleteReq {
        private Long hospitalIdx;
        private Long userIdx;

        public static DeleteReq from (Waiting entity) {
            return DeleteReq.builder()
                    .hospitalIdx(entity.getHospitalIdx())
                    .userIdx(entity.getUserIdx())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class DeleteRes {
        private Long hospitalIdx;
        private Long userIdx;
        private int waitingNumber;

        public static DeleteRes from (Waiting entity) {
            return DeleteRes.builder()
                    .hospitalIdx(entity.getHospitalIdx())
                    .userIdx(entity.getUserIdx())
                    .waitingNumber(entity.getWaitingNumber())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class FindRes {
        private Long idx;
        private Long hospitalIdx;
        @Setter
        private String userName;
        private int waitingNumber;

        public static FindRes from (Waiting entity) {
            return FindRes.builder()
                    .idx(entity.getIdx())
                    .hospitalIdx(entity.getHospitalIdx())
                    .waitingNumber(entity.getWaitingNumber())
                    .build();
        }
    }


    @Builder
    @Getter
    public static class findWaitingNumberReq {
        private Long hospitalIdx;
        private Long userIdx;

        public static findWaitingNumberReq from (Waiting entity) {
            return findWaitingNumberReq.builder()
                    .hospitalIdx(entity.getHospitalIdx())
                    .userIdx(entity.getUserIdx())
                    .build();
        }
    }


    @Builder
    @Getter
    public static class findWaitingNumberRes {
        private Long hospitalIdx;
        private Long userIdx;

        public static findWaitingNumberRes from(Waiting entity) {
            return findWaitingNumberRes.builder()
                    .hospitalIdx(entity.getHospitalIdx())
                    .userIdx(entity.getUserIdx())
                    .build();
        }
    }



    @Getter
    @Builder
    public static class isReservedRes {
        private Long userIdx;
        private Long hospitalIdx;
        private int waitingNumber;

        public static isReservedRes from (Waiting entity) {
            return isReservedRes.builder()
                    .userIdx(entity.getUserIdx())
                    .hospitalIdx(entity.getHospitalIdx())
                    .waitingNumber(entity.getWaitingNumber())
                    .build();
        }
    }
}
