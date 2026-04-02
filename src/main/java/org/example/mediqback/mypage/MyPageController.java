package org.example.mediqback.mypage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.addfamily.AddFamilyService;
import org.example.mediqback.addfamily.model.FamilyDto;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.medicalhistory.MedicalHistoryService;
import org.example.mediqback.medicalhistory.model.MedicalHistoryDto;
import org.example.mediqback.paymenthistory.PaymentHistoryService;
import org.example.mediqback.paymenthistory.model.PaymentHistoryDto;
import org.example.mediqback.user.model.AuthUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MyPage API", description = "마이페이지 (진료 기록, 처방전, 결제 내역 등) 조회 API")
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MedicalHistoryService medicalHistoryService;
    private final PaymentHistoryService paymentHistoryService;
    private final AddFamilyService addFamilyService;

    // 1. 진료 기록 탭 클릭 시 (또는 첫 화면)
    @Operation(summary = "진료 기록 조회", description = "현재 로그인한 사용자의 과거 진료 기록 내역을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/medical")
    public ResponseEntity<BaseResponse<List<MedicalHistoryDto.Response>>> getMyMedicalHistory(
            @AuthenticationPrincipal AuthUserDetails userDetails) {
        Long userIdx = userDetails.getIdx();
        return ResponseEntity.ok(BaseResponse.success(medicalHistoryService.getMyMedicalHistory(userIdx)));
    }

    // 처방전 탭 클릭 시 (나중에 PrescriptionService 연결)
    @Operation(summary = "처방전 조회", description = "현재 로그인한 사용자의 처방전 내역을 조회합니다. (임시 반환)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/prescriptions")
    public ResponseEntity<BaseResponse<List<Object>>> getMyPrescriptions(
            @AuthenticationPrincipal AuthUserDetails userDetails) {
        // 임시로 빈 배열 반환
        return ResponseEntity.ok(BaseResponse.success(List.of()));
    }

    @Operation(summary = "결제 내역 조회", description = "현재 로그인한 사용자의 앱 결제(예약금 등) 내역을 최신순으로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/billing")
    public ResponseEntity<BaseResponse<List<PaymentHistoryDto.Response>>> getMyBilling(
            @AuthenticationPrincipal AuthUserDetails userDetails) {

        Long userIdx = userDetails.getIdx(); // 현재 로그인한 사용자 식별자 가져오기

        // 서비스 로직을 호출하여 실제 결제 내역 데이터를 반환
        List<PaymentHistoryDto.Response> result = paymentHistoryService.getMyPaymentHistories(userIdx);

        return ResponseEntity.ok(BaseResponse.success(result));
    }

    // 검사 결과 탭 클릭 시 (나중에 ResultService 연결)
    @Operation(summary = "검사 결과 조회", description = "현재 로그인한 사용자의 검사 결과 내역을 조회합니다. (임시 반환)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/results")
    public ResponseEntity<BaseResponse<List<Object>>> getMyResults(
            @AuthenticationPrincipal AuthUserDetails userDetails) {
        // 임시로 빈 배열 반환
        return ResponseEntity.ok(BaseResponse.success(List.of()));
    }

    @PostMapping("/addfamily")
    public ResponseEntity reg(
            @AuthenticationPrincipal AuthUserDetails userDetails,
            @RequestBody FamilyDto.Req dto) {
        FamilyDto.Res result = addFamilyService.reg(dto);
        return ResponseEntity.ok(result);
    }

//    @GetMapping("/familyinfo")
//    public ResponseEntity familyinfo() {
//
//    }
}