package org.example.mediqback.waiting;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.queue.QueueService;
import org.example.mediqback.queue.model.Queue;
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

    @PostMapping("/register")
    public ResponseEntity register(Long userIdx, Long hospitalIdx) {
        WaitingDto.WaitingReq dto = new WaitingDto.WaitingReq();

        dto.setUserIdx(userIdx);

        dto.setHospitalIdx(hospitalIdx);

        Queue queue = queueService.findQueueByHospitalIdx(hospitalIdx);

        dto.setWaitingNumber(queue.getLastNo() + 1);

        WaitingDto.WaitingRes result  = waitingService.register(dto);

        return ResponseEntity.ok(BaseResponse.success(result));
    }


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
