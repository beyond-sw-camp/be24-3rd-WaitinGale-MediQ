package org.example.mediqback.Surveyform.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class SurveyformDto {

    @Schema(description = "문진표 생성 요청 DTO")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReq {
        @Schema(description = "증상 목록", example = "[\"두통\", \"기침\"]")
        private List<String> symptoms;
        @Schema(description = "기타 증상", example = "속이 쓰립니다.")
        private String otherSymptom;

        // 날짜가 빈 값으로 올 수 있으므로 LocalDate 객체 사용
        @Schema(description = "증상 발현 시작일", example = "2026-03-20")
        private LocalDate symptomStartDate;

        // 예/아니오 항목은 반드시 넘어오도록 @NotNull 처리
        @Schema(description = "현재 복용 중인 약 여부", example = "true")
        @NotNull(message = "현재 복용 중인 약 여부를 선택해주세요.")
        private Boolean takingMedicine;
        @Schema(description = "복용 중인 약물 상세", example = "고혈압 약")
        private String medicineDetail;

        @Schema(description = "기저 질환 목록", example = "[\"고혈압\"]")
        private List<String> diseases;

        @Schema(description = "알레르기 여부", example = "false")
        @NotNull(message = "알레르기 여부를 선택해주세요.")
        private Boolean allergy;
        @Schema(description = "알레르기 상세 정보", example = "땅콩 알레르기")
        private String allergyDetail;

        @Schema(description = "최근 2주 이내 해외 방문 여부", example = "false")
        @NotNull(message = "최근 2주 이내 해외 방문 여부를 선택해주세요.")
        private Boolean travel;

        public Surveyform toEntity() {
            return Surveyform.builder()
                    .symptoms(this.symptoms)
                    .otherSymptom(this.otherSymptom)
                    .symptomStartDate(this.symptomStartDate)
                    .takingMedicine(this.takingMedicine)
                    .medicineDetail(this.medicineDetail)
                    .diseases(this.diseases)
                    .allergy(this.allergy)
                    .allergyDetail(this.allergyDetail)
                    .travel(this.travel)
                    .build();
        }
    }
}