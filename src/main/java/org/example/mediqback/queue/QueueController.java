package org.example.mediqback.queue;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.queue.model.QueueDto;
import org.example.mediqback.waiting.WaitingService;
import org.example.mediqback.waiting.model.WaitingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/waiting")
@RequiredArgsConstructor
public class QueueController {
    private final QueueService queueService;

    @PostMapping("/next")
    public ResponseEntity<QueueDto.NextRes> findNextQueue(@RequestBody QueueDto.NextReq queueDto) {

        QueueDto.NextRes nextResult = queueService.findQueue(queueDto.getHospitalIdx());

        return ResponseEntity.ok(nextResult);
    }
}
