package org.example.mediqback.waiting;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.queue.QueueService;
import org.example.mediqback.waiting.model.Waiting;
import org.example.mediqback.waiting.model.WaitingDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WaitingService {
    private final WaitingRepository waitingRepository;
    private final QueueService queueService;

    // 대기열 등록하기
    public WaitingDto.WaitingRes register(/*WaitingDto.WaitingReq dto*/ Long hospitalIdx, Long userIdx) {
        // 일단 병원만 프론트에서 검색해서 전달받기로
//         전달받던 매개변수
//        Waiting entity = waitingRepository.save(dto.toEntity(dto));
//        return WaitingDto.WaitingRes.from(entity);

        int nextNo = queueService.generateNextWaitingNumber(hospitalIdx);

        WaitingDto.WaitingReq dto = WaitingDto.WaitingReq.builder()
                                                .hospitalIdx(hospitalIdx)
                                                .userIdx(userIdx)
                                                .waitingNumber(nextNo)
                                                .build();

        Waiting waiting = waitingRepository.save(dto.toEntity(dto));

        return WaitingDto.WaitingRes.from(waiting);
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
        for (Waiting waiting : waitingEntityList) {
            listResDtoList.add(WaitingDto.ListRes.from(waiting));
        }
        return listResDtoList;
    }




    // 대기열 삭제하기
    public WaitingDto.DeleteRes deleteRegistration (WaitingDto.DeleteReq dto) {
        Waiting waiting = waitingRepository.findByHospitalIdxAndUserIdx(dto.getHospitalIdx(), dto.getUserIdx());
        waitingRepository.delete(waiting);
        return WaitingDto.DeleteRes.from(waiting);
    }



    // 병원 idx와 로그인 한 사용자 idx로 병원 예약 대기열에 있는지 확인
    public WaitingDto.FindRes findWaiting(Long hospitalIdx, Long userIdx, String userName) {
        Waiting waiting = waitingRepository.findByHospitalIdxAndUserIdx(hospitalIdx, userIdx);

        // 조회 결과가 있다면
        if (waiting != null) {
            WaitingDto.FindRes findRes = WaitingDto.FindRes.from(waiting);
            findRes.setUserName(userName);
            return findRes;
        } else {
            // 조회 결과가 없다면
            WaitingDto.FindRes findRes = WaitingDto.FindRes.builder()
                                                    .idx(-1L)
                                                    .build();
            return findRes;
        }
    }


}
