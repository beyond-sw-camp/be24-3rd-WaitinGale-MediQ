package org.example.mediqback.notification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.notification.model.NotificationDto;
import org.example.mediqback.user.model.AuthUserDetails;
import org.jose4j.lang.JoseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutionException;

@Tag(name = "Notification API", description = "웹 푸시 알림 등록 및 발송 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/push")
@CrossOrigin(origins = "*")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "푸시 알림 구독", description = "사용자의 브라우저에서 생성된 푸시 구독 정보를 서버에 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "구독 성공")
    })
    @PostMapping("/sub")
    public ResponseEntity subscribe(
            @AuthenticationPrincipal AuthUserDetails user,
            @RequestBody NotificationDto.Subscribe dto
    ) {
        dto.setUserIdx(user.getIdx());
        notificationService.subscribe(dto);
        return ResponseEntity.ok("성공");
    }

    @Operation(summary = "푸시 알림 발송", description = "특정 사용자에게 웹 푸시 알림을 발송합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "발송 성공")
    })
    @PostMapping("/send")
    public ResponseEntity send(@RequestBody NotificationDto.Send dto) throws JoseException, GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        notificationService.send(dto);

        return ResponseEntity.ok("성공");
    }
}