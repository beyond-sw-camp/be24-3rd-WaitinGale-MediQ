package org.example.mediqback.Surveyform;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.Surveyform.model.SurveyformDto;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.common.model.BaseResponseStatus;
import org.example.mediqback.user.model.AuthUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
@CrossOrigin
public class SurveyformController {

    private final SurveyformService surveyformService;

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