package org.example.mediqback.medicalhistory.model;

import lombok.Builder;
import lombok.Getter;

public class MedicalHistoryDto
{

    @Getter
    @Builder
    public static class Response
    {
        private String hospital;
        private String department;
        private String date;
        private String doctor;
        private String diagnosis;
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
