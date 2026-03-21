package org.example.mediqback.waiting;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.waiting.model.Waiting;
import org.example.mediqback.waiting.model.WaitingDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    // 현재 대기열 정보 가져오기
    public List<WaitingDto.ListRes> findListByHospitalId(Long hospitalIdx) {
        List<Waiting> waitingEntityList = waitingRepository.findAllByHospitalIdx(hospitalIdx);
        List<WaitingDto.ListRes> listResDtoList = new ArrayList<>();
        for (Waiting entity : waitingEntityList) {
            listResDtoList.add(WaitingDto.ListRes.from(entity));
        }
        return listResDtoList;
    }

    // 대기열 삭제하기
    public WaitingDto.DeleteRes deleteRegistration (WaitingDto.DeleteReq dto) {
        Waiting entity = waitingRepository.findByHospitalIdxAndUserIdx(dto.getHospitalIdx(), dto.getUserIdx());
        waitingRepository.delete(entity);
        return WaitingDto.DeleteRes.from(entity);
    }

}
