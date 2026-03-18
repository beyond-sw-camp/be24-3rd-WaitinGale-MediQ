package org.example.mediqback.medicalhistory;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.medicalhistory.model.MedicalHistory;
import org.example.mediqback.medicalhistory.model.MedicalHistoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalHistoryService
{
    private final MedicalHistoryRepository medicalHistoryRepository;

    public List<MedicalHistoryDto.Response> getMyMedicalHistory(Long userIdx) {
        List<MedicalHistory> historyList = medicalHistoryRepository.findAllByUserIdxOrderByTreatmentDateDesc(userIdx);

        return historyList.stream()
                .map(MedicalHistoryDto.Response::from)
                .collect(Collectors.toList());
    }
}
