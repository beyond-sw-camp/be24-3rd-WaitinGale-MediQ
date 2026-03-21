package org.example.mediqback.waiting;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.queue.QueueService;
import org.example.mediqback.queue.model.Queue;
import org.example.mediqback.queue.model.QueueDto;
import org.example.mediqback.user.model.AuthUserDetails;
import org.example.mediqback.waiting.model.Waiting;
import org.example.mediqback.waiting.model.WaitingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity register(@RequestBody WaitingDto.WaitingReq waitingDto,
                                   @AuthenticationPrincipal AuthUserDetails user) {

        // 등록할 때 사용자 idx 입력받는게 아니라 로그인 한 사용자 idx 받아오도록
        // 프론트에서 직접 입력할 정보는 지금은 병원 이름만
        // 나중에는 병원 검색 후 idx 를 받아오도록

        WaitingDto.WaitingRes waitingResult = waitingService.register(waitingDto.getHospitalIdx(), user.getIdx());

        return ResponseEntity.ok(BaseResponse.success(waitingResult));
    }

    @DeleteMapping("/register")
    public ResponseEntity deleteReggistration(@RequestBody WaitingDto.DeleteReq waitingDto) {
        WaitingDto.DeleteRes deleteResult = waitingService.deleteRegistration(waitingDto);

        return ResponseEntity.ok(BaseResponse.success(deleteResult));
    }

    @GetMapping("/myOrder")
    public ResponseEntity checkMyOrder(Long userIdx, Long hospitalIdx) {
        int currentTreatmentNumber = queueService.findQueueByHospitalIdx(hospitalIdx).getCurrentNo();

        int myNumber = waitingService.findMyOrder(userIdx);

        return ResponseEntity.ok(BaseResponse.success("내 대기 번호 " + String.valueOf(myNumber - currentTreatmentNumber)));
    }



    // 검색한 병원의 대기열 정보 불러오기
//    @GetMapping("/queue/list/{hospitalIdx}")
//    public ResponseEntity findQueueListByHospitalIdx(
//            @PathVariable("hospitalIdx") Long hospitalIdx
//    ) {
//        List<WaitingDto.ListRes> waitingList = waitingService.findListByHospitalId(hospitalIdx);
//        return ResponseEntity.ok(waitingList);
//    }


    // 대기열 정보를 불러올 때 앞의 사람까지 전부 불러올 필요 없이
    // 제일 마지막 번호만 불러오면 될 듯
    // 검색한 병원과 현재 로그인 한 사용자를 검색해서 있으면 띄워서 보여주기
    // 없으면 대기 화면으로
    @GetMapping("/queue/{hospitalIdx}")
    public ResponseEntity findWaiting(
            @PathVariable("hospitalIdx") Long hospitalIdx
//            @AuthenticationPrincipal AuthUserDetails user
    ) {
        Long userIdx = 1L;
//        WaitingDto.FindRes findRes = waitingService.findWaiting(hospitalIdx, user.getIdx());
        WaitingDto.FindRes findRes = waitingService.findWaiting(hospitalIdx, userIdx);

        return ResponseEntity.ok(findRes);
    }



}
