package org.example.mediqback.Surveyform.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class SurveyformDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReq {
        private List<String> symptoms;
        private String otherSymptom;

        // 날짜가 빈 값으로 올 수 있으므로 LocalDate 객체 사용
        private LocalDate symptomStartDate;

        // 예/아니오 항목은 반드시 넘어오도록 @NotNull 처리
        @NotNull(message = "현재 복용 중인 약 여부를 선택해주세요.")
        private Boolean takingMedicine;
        private String medicineDetail;

        private List<String> diseases;

        @NotNull(message = "알레르기 여부를 선택해주세요.")
        private Boolean allergy;
        private String allergyDetail;

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