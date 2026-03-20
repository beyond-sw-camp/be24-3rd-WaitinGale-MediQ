package org.example.mediqback.waiting;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.queue.QueueService;
import org.example.mediqback.queue.model.Queue;
import org.example.mediqback.queue.model.QueueDto;
import org.example.mediqback.waiting.model.Waiting;
import org.example.mediqback.waiting.model.WaitingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/waiting")
@RequiredArgsConstructor
public class WaitingController {
    private final WaitingService waitingService;
    private final QueueService queueService;

    // 대기열 등록
//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody WaitingDto.WaitingReq waitingDto) {
//
//        // 대기열의 마지막 번호 + 1을 새로 대기열 등록한 사람의 대기열 번호로 등록시키기 위해
//        // 병원 idx로 병원의 마지막 대기열 번호를 조회
//        // default = 0 으로 초기화 하면 아래 조건문 없어도 될 것 같기도?
//        int currentNumber = 0;
//        Queue queue = queueService.findQueueByHospitalIdx(waitingDto.getHospitalIdx());
//        if (queue != null) {
//            currentNumber = queue.getCurrentNo();
//            waitingDto.setWaitingNumber(queue.getLastNo() + 1);
//        } else {
//            waitingDto.setWaitingNumber(1);
//        }
//
//        // queue 테이블에도 저장해줘야함
//        /*
//        current_no, hospital_idx, last_no, updated_at
//        */
//
//
//        QueueDto.QueueReq queueDto = QueueDto.QueueReq.builder()
//                                            .hospitalIdx(waitingDto.getHospitalIdx())
//                                            .lastNo(waitingDto.getWaitingNumber())
//                                            .currentNo(currentNumber)
//                                            .build();
//
//        // waiting 테이블에 저장
//
//        WaitingDto.WaitingRes waitingResult  = waitingService.register(waitingDto);
//
//
//
//
//        // 여기서 매 행 마다 새로 저장하는게 아니라 없으면 새로 저장하고 있으면 변경해주기
//        QueueDto.QueueRes queueResult = queueService.register(queueDto);
//        // 이거 수정해야됨
//
//        return ResponseEntity.ok(BaseResponse.success(waitingResult + " " + queueResult));
//    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody WaitingDto.WaitingReq waitingDto) {
        int nextNo = queueService.generateNextWaitingNumber(waitingDto.getHospitalIdx());

        waitingDto.setWaitingNumber(nextNo);

        WaitingDto.WaitingRes waitingResult = waitingService.register(waitingDto);

        return ResponseEntity.ok(BaseResponse.success(waitingResult));


    }


//    @DeleteMapping("/register")
//    public ResponseEntity deleteRegistration(@RequestBody WaitingDto.)


    @GetMapping("/myOrder")
    public ResponseEntity checkMyOrder(Long userIdx, Long hospitalIdx) {
        int currentTreatmentNumber = queueService.findQueueByHospitalIdx(hospitalIdx).getCurrentNo();

        int myNumber = waitingService.findMyOrder(userIdx);

        return ResponseEntity.ok(BaseResponse.success("내 대기 번호 " + String.valueOf(myNumber - currentTreatmentNumber)));
    }


    @GetMapping("/queue/list/{hospitalIdx}")
    public ResponseEntity findQueueListByHospitalIdx(
            @PathVariable("hospitalIdx") Long hospitalIdx
    ) {
        List<WaitingDto.ListRes> waitingList = waitingService.findListByHospitalId(hospitalIdx);

        return ResponseEntity.ok(waitingList);
    }

}
