package org.example.mediqback.queue;

import org.example.mediqback.queue.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueRepository extends JpaRepository<Queue, Long> {
    Queue findByHospitalIdx(Long hospitalIdx);
}
