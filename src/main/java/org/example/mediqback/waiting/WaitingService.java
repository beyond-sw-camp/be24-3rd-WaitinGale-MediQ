package org.example.mediqback.waiting;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.waiting.model.Waiting;
import org.example.mediqback.waiting.model.WaitingDto;
import org.springframework.stereotype.Service;

import static org.json.XMLTokener.entity;

@Service
@RequiredArgsConstructor
public class WaitingService {
    private final WaitingRepository waitingRepository;

    public WaitingDto.WaitingRes register(WaitingDto.WaitingReq dto) {
        Waiting entity = waitingRepository.save(dto.toEntity(dto));
        return WaitingDto.WaitingRes.from(entity);
    }

    public int findMyOrder(Long userIdx) {
        Waiting waiting = waitingRepository.findByUserIdx(userIdx);
        if (waiting != null) {
            return waiting.getWaitingNumber();
        } else {
            return -1;
        }
    }

}
