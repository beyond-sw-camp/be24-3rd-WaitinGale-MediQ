package org.example.mediqback.queue;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.hospital.HospitalService;
import org.example.mediqback.queue.model.QueueDto;
import org.example.mediqback.waiting.WaitingService;
import org.example.mediqback.waiting.model.WaitingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waiting")
@RequiredArgsConstructor
public class QueueController {
    private final QueueService queueService;
    private final HospitalService hospitalService;

    @PostMapping("/next")
    public ResponseEntity<QueueDto.NextRes> findNextQueue(@RequestBody QueueDto.NextReq queueDto) {
        QueueDto.NextRes nextResult = queueService.findQueue(queueDto.getHospitalIdx());
        return ResponseEntity.ok(nextResult);
    }

    @GetMapping("/api/hospitals/list")
    public ResponseEntity<?> getHospitalList() {
        // 이제 hospitalService를 정상적으로 호출할 수 있습니다.
        return ResponseEntity.ok(hospitalService.findAll());
    }
}
