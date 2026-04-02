package org.example.mediqback.waiting;

import org.example.mediqback.waiting.model.Waiting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {
    Waiting findByUserIdx(Long userIdx);

//    List<Waiting> findAllByHospitalIdx(Long hospitalIdx);

    Page<Waiting> findAllByHospitalIdx(Long hospitalIdx, Pageable pageable);

    Waiting findByHospitalIdxAndUserIdx(Long hospitalIdx, Long userIdx);

    // hospitalIdx가 일치하고, waitingNumber가 특정 값보다 큰 리스트 조회
    List<Waiting> findByHospitalIdxAndWaitingNumberGreaterThan(Long hospitalIdx, int waitingNumber);

    Waiting findByHospitalIdxAndWaitingNumber(Long hospitalIdx, int i);

}
