package org.example.mediqback.queue;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.queue.model.Queue;
import org.example.mediqback.queue.model.QueueDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {
    private final QueueRepository queueRepository;

    public Queue findQueueByHospitalIdx(Long hospitalIdx) {
        return queueRepository.findByHospitalIdx(hospitalIdx);
    }


    // test
    public int generateNextWaitingNumber(Long hospitalIdx) {
        // 1. 해당 병원의 큐 정보를 조회 (없으면 새로 생성)
        Queue queue = queueRepository.findByHospitalIdx(hospitalIdx);

        if (queue == null) {
            queue = Queue.builder()
                    .hospitalIdx(hospitalIdx)
                    .lastNo(1)
                    .currentNo(0)
                    .build();
        } else {
            queue.updateLastNo(queue.getLastNo() + 1);
        }

        queueRepository.save(queue);
        return queue.getLastNo();
    }






    // queue 등록하기
    // 병원이 이미 등록되어 있으면 그 번호에 + 1을 하고 병원이 없으면 새로 병원 추가해주기
    public QueueDto.QueueRes register(QueueDto.QueueReq dto) {

        Queue queue = queueRepository.findByHospitalIdx(dto.getHospitalIdx());

        if (queue != null) {
                   dto.builder()
                    .lastNo(dto.getLastNo() + 1)
                    .build();
        }

        Queue entity = queueRepository.save(dto.toEntity(dto));
        return QueueDto.QueueRes.from(entity);
    }



    public QueueDto.NextRes findQueue(Long hospitalIdx) {

        Queue queue = queueRepository.findByHospitalIdx(hospitalIdx);

        if (queue == null) {
            throw new IllegalArgumentException("해당 병원의 대기열이 존재하지 않습니다");
        }

        queue.updateCurrentNo(queue.getCurrentNo() + 1);

        queueRepository.save(queue);

        return QueueDto.NextRes.builder()
                .hospitalIdx(queue.getHospitalIdx())
                .currentNo(queue.getCurrentNo())
                .lastNo(queue.getLastNo())
                .build();


    }



}
