package org.example.mediqback.orders;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Orders API", description = "예약 주문 및 포트원 결제 관리 API") // 그룹 지정
@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrdersController {
    private final OrdersService ordersService;

    @Operation(summary = "결제 대기 주문서 생성", description = "프론트엔드에서 결제창을 띄우기 직전에 결제 대기 상태의 주문을 만듭니다.")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse> create(
            @AuthenticationPrincipal AuthUserDetails authUserDetails,
            @Valid @RequestBody OrdersDto.OrdersReq dto) {
        OrdersDto.OrdersRes response = ordersService.create(authUserDetails, dto);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    // OrdersController.java (성능 테스트용 임시 API)
    @Operation(summary = "결제 검증 (테스트용)", description = "실제 외부 PG사 연동 없이 테스트 결제를 완료 처리합니다.")
    @PostMapping("/test-verify")
    public ResponseEntity testVerify(
            @AuthenticationPrincipal AuthUserDetails user,
            @RequestBody OrdersDto.VerifyReq dto
    ) {
        // 실제 외부 연동 코드가 있는 ordersService.verify() 대신 테스트용 메서드 호출
        ordersService.testVerify(user, dto);
        return ResponseEntity.ok("테스트 결제 완료");
    }

    @Operation(summary = "포트원 결제 검증", description = "포트원 결제 완료 후 실제 결제 금액과 DB의 예약금을 검증하고 결제 완료 처리합니다.")
    @PostMapping("/verify")
    public ResponseEntity verify(
            @AuthenticationPrincipal AuthUserDetails user,
            @RequestBody OrdersDto.VerifyReq dto
    ) {
        ordersService.verify(user, dto);
        return ResponseEntity.ok("성공");
    }

    // 프론트엔드가 일정을 요청할 때 응답해주는 API
    @Operation(summary = "내 예약 일정 조회", description = "마이페이지에 보여줄 확정된 내 예약 일정(결제 완료건)을 가져옵니다.")
    @GetMapping("/schedule")
    public ResponseEntity<?> getMySchedule(@AuthenticationPrincipal AuthUserDetails user) {
        List<Map<String, Object>> result = ordersService.getMySchedule(user);
        return ResponseEntity.ok(Map.of("success", true, "result", result));
    }
}