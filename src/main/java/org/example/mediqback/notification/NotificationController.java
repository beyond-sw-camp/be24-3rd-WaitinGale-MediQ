package org.example.mediqback.notification;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.notification.model.NotificationDto;
import org.jose4j.lang.JoseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/push")
@CrossOrigin(origins = "*")
public class NotificationController {
    private final NotificationService notificationService;
    @PostMapping("/sub/{userIdx}")
    public ResponseEntity subscribe(
            @PathVariable("userIdx") Long userIdx,
            @RequestBody NotificationDto.Subscribe dto
    ) {
        dto.setUserIdx(userIdx);
        notificationService.subscribe(dto);
        return ResponseEntity.ok("성공");
    }

    @PostMapping("/send")
    public ResponseEntity send(@RequestBody NotificationDto.Send dto) throws JoseException, GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        notificationService.send(dto);

        return ResponseEntity.ok("성공");
    }
}