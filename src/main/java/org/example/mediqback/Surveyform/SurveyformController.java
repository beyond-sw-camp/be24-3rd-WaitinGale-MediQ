package org.example.mediqback.Surveyform;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.Surveyform.model.SurveyformDto;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.common.model.BaseResponseStatus;
import org.example.mediqback.user.model.AuthUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Surveyform API", description = "문진표 작성 API")
@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
@CrossOrigin
public class SurveyformController {

    private final SurveyformService surveyformService;

    @Operation(summary = "문진표 제출", description = "사용자가 작성한 문진표 데이터를 제출합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "문진표 제출 성공"),
            @ApiResponse(responseCode = "401", description = "로그인되지 않은 사용자")
    })
    @PostMapping
    public ResponseEntity<?> submitSurvey(
            @AuthenticationPrincipal AuthUserDetails userDetails,
            @Valid @RequestBody SurveyformDto.CreateReq dto) {

        // 로그인되지 않은 사용자인 경우 예외 처리
        if (userDetails == null) {
            return ResponseEntity.status(401).body(BaseResponse.fail(BaseResponseStatus.JWT_INVALID));
        }

        surveyformService.createSurvey(userDetails.getIdx(), dto);

        return ResponseEntity.ok(BaseResponse.success("문진표 제출이 완료되었습니다."));
    }
}