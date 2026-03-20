package org.example.mediqback.medicalhistory;

import org.example.mediqback.medicalhistory.model.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    List<MedicalHistory> findAllByUserIdxOrderByTreatmentDateDesc(Long userIdx);
}