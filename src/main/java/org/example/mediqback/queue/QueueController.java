package org.example.mediqback.queue;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.queue.model.QueueDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Queue API", description = "병원 대기열 및 순서 관리 API")
@RestController
@RequestMapping("/waiting")
@RequiredArgsConstructor
public class QueueController {
    private final QueueService queueService;

    @Operation(summary = "다음 순서 호출", description = "대기열에서 다음 순서의 환자를 호출합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "다음 순서 호출 성공")
    })
    @PostMapping("/next")
    public ResponseEntity<QueueDto.NextRes> findNextQueue(@RequestBody QueueDto.NextReq queueDto) {

        QueueDto.NextRes nextResult = queueService.findQueue(queueDto.getHospitalIdx());

        return ResponseEntity.ok(nextResult);
    }
}