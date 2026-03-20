package org.example.mediqback.mypage;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.medicalhistory.MedicalHistoryService;
import org.example.mediqback.medicalhistory.model.MedicalHistoryDto;
import org.example.mediqback.paymenthistory.PaymentHistoryService;
import org.example.mediqback.paymenthistory.model.PaymentHistoryDto;
import org.example.mediqback.user.model.AuthUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MedicalHistoryService medicalHistoryService;
    private final PaymentHistoryService paymentHistoryService;

    // 진료 기록 탭 클릭 시 (또는 첫 화면)
    @GetMapping("/medical")
    public ResponseEntity<BaseResponse<List<MedicalHistoryDto.Response>>> getMyMedicalHistory(
            @AuthenticationPrincipal AuthUserDetails userDetails) {
        Long userIdx = userDetails.getIdx();
        return ResponseEntity.ok(BaseResponse.success(medicalHistoryService.getMyMedicalHistory(userIdx)));
    }

    // 처방전 탭 클릭 시
    @GetMapping("/prescriptions")
    public ResponseEntity<BaseResponse<List<Object>>> getMyPrescriptions(
            @AuthenticationPrincipal AuthUserDetails userDetails) {
        // 임시로 빈 배열 반환
        return ResponseEntity.ok(BaseResponse.success(List.of()));
    }

    @GetMapping("/billing")
    public ResponseEntity<BaseResponse<List<PaymentHistoryDto.Response>>> getMyBilling(
            @AuthenticationPrincipal AuthUserDetails userDetails) {

        Long userIdx = userDetails.getIdx(); // 현재 로그인한 사용자 식별자 가져오기

        // 결제 내역 데이터를 반환
        List<PaymentHistoryDto.Response> result = paymentHistoryService.getMyPaymentHistories(userIdx);

        return ResponseEntity.ok(BaseResponse.success(result));
    }

    // 검사 결과 탭 클릭 시
    @GetMapping("/results")
    public ResponseEntity<BaseResponse<List<Object>>> getMyResults(
            @AuthenticationPrincipal AuthUserDetails userDetails) {
        // 임시로 빈 배열 반환
        return ResponseEntity.ok(BaseResponse.success(List.of()));
    }
}