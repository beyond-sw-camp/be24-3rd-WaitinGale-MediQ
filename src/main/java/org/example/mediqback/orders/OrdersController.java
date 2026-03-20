package org.example.mediqback.orders;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.orders.model.OrdersDto;
import org.example.mediqback.user.model.AuthUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/verify")
    public ResponseEntity verify(
            @AuthenticationPrincipal AuthUserDetails user,
            @RequestBody OrdersDto.VerifyReq dto
    ) {
        ordersService.verify(user, dto);

        return ResponseEntity.ok("성공");
    }

}