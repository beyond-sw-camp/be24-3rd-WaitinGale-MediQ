package org.example.mediqback.queue;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.queue.model.Queue;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {
    private QueueRepository queueRepository;

    public Queue findQueueByHospitalIdx(Long hospitalIdx) {
        return queueRepository.findByHospitalIdx(hospitalIdx);
    }
}
