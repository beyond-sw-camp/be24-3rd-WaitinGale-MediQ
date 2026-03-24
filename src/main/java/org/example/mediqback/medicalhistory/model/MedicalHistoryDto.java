package org.example.mediqback.medicalhistory.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class MedicalHistoryDto
{

    @Schema(description = "진료 기록 응답 DTO")
    @Getter
    @Builder
    public static class Response
    {
        @Schema(description = "병원명", example = "서울대학교병원")
        private String hospital;
        @Schema(description = "진료과", example = "내과")
        private String department;
        @Schema(description = "진료 날짜", example = "2026-03-24")
        private String date;
        @Schema(description = "담당 의사", example = "김원장")
        private String doctor;
        @Schema(description = "진단명", example = "감기")
        private String diagnosis;
        @Schema(description = "처방 내용", example = "타이레놀 3일치")
        private String prescription;

        public static Response from(MedicalHistory entity)
        {
            return Response.builder()
                    .hospital(entity.getHospital())
                    .department(entity.getDepartment())
                    .date(entity.getTreatmentDate())
                    .doctor(entity.getDoctor())
                    .diagnosis(entity.getDiagnosis())
                    .prescription(entity.getPrescription())
                    .build();
        }
    }
}