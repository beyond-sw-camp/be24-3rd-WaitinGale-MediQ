package org.example.mediqback.waiting.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

public class WaitingDto {
    @Schema(description = "대기 등록 요청 DTO")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WaitingReq {
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "사용자 고유 번호", example = "1")
        private Long userIdx;
        @Schema(description = "대기 번호", example = "5")
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

    @Schema(description = "대기 등록 응답 DTO")
    @Builder
    @Getter
    public static class WaitingRes {
        @Schema(description = "사용자 고유 번호", example = "1")
        private Long userIdx;
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "대기 상태", example = "WAITING")
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

    @Schema(description = "내 순서 정보 DTO")
    @Builder
    public static class MyOrder {
        @Schema(description = "사용자 고유 번호", example = "1")
        private Long UserIdx;
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "대기 번호", example = "5")
        private int waitingNumber;

        public static Waiting from(Waiting entity) {
            return Waiting.builder()
                    .waitingNumber(entity.getWaitingNumber())
                    .build();
        }
    }

    @Schema(description = "대기열 목록 응답 DTO")
    @Builder
    @Getter
    public static class ListRes {
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "사용자 고유 번호", example = "1")
        private Long userIdx;
        @Schema(description = "대기 번호", example = "5")
        private int waitingNumber;
        @Schema(description = "대기 상태", example = "WAITING")
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

    @Schema(description = "대기 취소 요청 DTO")
    @Getter
    @Builder
    public static class DeleteReq {
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "사용자 고유 번호", example = "1")
        private Long userIdx;

        public static DeleteReq from (Waiting entity) {
            return DeleteReq.builder()
                    .hospitalIdx(entity.getHospitalIdx())
                    .userIdx(entity.getUserIdx())
                    .build();
        }
    }

    @Schema(description = "대기 취소 응답 DTO")
    @Getter
    @Builder
    public static class DeleteRes {
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "사용자 고유 번호", example = "1")
        private Long userIdx;
        @Schema(description = "취소된 대기 번호", example = "5")
        private int waitingNumber;

        public static DeleteRes from (Waiting entity) {
            return DeleteRes.builder()
                    .hospitalIdx(entity.getHospitalIdx())
                    .userIdx(entity.getUserIdx())
                    .waitingNumber(entity.getWaitingNumber())
                    .build();
        }
    }

    @Schema(description = "예약 확인 응답 DTO")
    @Getter
    @Builder
    public static class FindRes {
        @Schema(description = "대기열 고유 번호", example = "1")
        private Long idx;
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "사용자 이름", example = "홍길동")
        @Setter
        private String userName;
        @Schema(description = "대기 번호", example = "5")
        private int waitingNumber;

        public static FindRes from (Waiting entity) {
            return FindRes.builder()
                    .idx(entity.getIdx())
                    .hospitalIdx(entity.getHospitalIdx())
                    .waitingNumber(entity.getWaitingNumber())
                    .build();
        }
    }

    @Schema(description = "대기 번호 찾기 요청 DTO")
    @Builder
    @Getter
    public static class findWaitingNumberReq {
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "사용자 고유 번호", example = "1")
        private Long userIdx;

        public static findWaitingNumberReq from (Waiting entity) {
            return findWaitingNumberReq.builder()
                    .hospitalIdx(entity.getHospitalIdx())
                    .userIdx(entity.getUserIdx())
                    .build();
        }
    }

    @Schema(description = "대기 번호 찾기 응답 DTO")
    @Builder
    @Getter
    public static class findWaitingNumberRes {
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "사용자 고유 번호", example = "1")
        private Long userIdx;

        public static findWaitingNumberRes from(Waiting entity) {
            return findWaitingNumberRes.builder()
                    .hospitalIdx(entity.getHospitalIdx())
                    .userIdx(entity.getUserIdx())
                    .build();
        }
    }

    @Schema(description = "예약 여부 확인 응답 DTO")
    @Getter
    @Builder
    public static class isReservedRes {
        @Schema(description = "사용자 고유 번호", example = "1")
        private Long userIdx;
        @Schema(description = "병원 고유 번호", example = "1")
        private Long hospitalIdx;
        @Schema(description = "대기 번호", example = "5")
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