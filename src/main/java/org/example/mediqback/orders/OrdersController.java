package org.example.mediqback.orders;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.orders.model.OrdersDto;
import org.example.mediqback.user.model.AuthUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrdersController {
    private final OrdersService ordersService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> create(
            @AuthenticationPrincipal AuthUserDetails authUserDetails,
            @Valid @RequestBody OrdersDto.OrdersReq dto) {
        OrdersDto.OrdersRes response = ordersService.create(authUserDetails, dto);

        return ResponseEntity.ok(BaseResponse.success(response));
    }

    // OrdersController.java (성능 테스트용 임시 API)
    @PostMapping("/test-verify")
    public ResponseEntity testVerify(
            @AuthenticationPrincipal AuthUserDetails user,
            @RequestBody OrdersDto.VerifyReq dto
    ) {
        // 실제 외부 연동 코드가 있는 ordersService.verify() 대신 테스트용 메서드 호출
        ordersService.testVerify(user, dto);
        return ResponseEntity.ok("테스트 결제 완료");
    }

    @PostMapping("/verify")
    public ResponseEntity verify(
            @AuthenticationPrincipal AuthUserDetails user,
            @RequestBody OrdersDto.VerifyReq dto
    ) {
        ordersService.verify(user, dto);

        return ResponseEntity.ok("성공");
    }

    // 프론트엔드가 일정을 요청할 때 응답해주는 API
    @GetMapping("/schedule")
    public ResponseEntity<?> getMySchedule(@AuthenticationPrincipal AuthUserDetails user) {
        // 로그인한 유저의 예약 일정을 서비스에서 가져와서 프론트로 응답
        List<Map<String, Object>> result = ordersService.getMySchedule(user);
        return ResponseEntity.ok(Map.of("success", true, "result", result));
    }

}