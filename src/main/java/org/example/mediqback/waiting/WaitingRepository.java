package org.example.mediqback.waiting;

import org.example.mediqback.waiting.model.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {
    Waiting findByUserIdx(Long userIdx);
}
