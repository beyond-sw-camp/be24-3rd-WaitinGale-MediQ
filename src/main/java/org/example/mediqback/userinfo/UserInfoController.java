package org.example.mediqback.userinfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.user.model.AuthUserDetails;
import org.example.mediqback.userinfo.model.UserInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserInfo API", description = "마이페이지 개인 정보 및 의료 정보 관리 API")
@RestController
@RequestMapping("/api/mypage/info")
@RequiredArgsConstructor
@CrossOrigin
public class UserInfoController {

    private final UserInfoService userInfoService;

    // 정보 조회 API
    @Operation(summary = "개인/의료 정보 조회", description = "현재 로그인된 사용자의 저장된 의료 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<BaseResponse<UserInfoDto>> getInfo(@AuthenticationPrincipal AuthUserDetails userDetails) {
        // AuthUserDetails의 username은 이메일을 의미합니다.
        UserInfoDto userInfo = userInfoService.getUserInfo(userDetails.getUsername());
        return ResponseEntity.ok(BaseResponse.success(userInfo));
    }

    // 정보 수정 API
    @Operation(summary = "개인/의료 정보 수정", description = "현재 로그인된 사용자의 의료 정보를 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정보 업데이트 성공")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<String>> updateInfo(
            @AuthenticationPrincipal AuthUserDetails userDetails,
            @RequestBody UserInfoDto requestDto) {

        userInfoService.updateUserInfo(userDetails.getUsername(), requestDto);
        return ResponseEntity.ok(BaseResponse.success("의료 및 개인 정보가 성공적으로 업데이트되었습니다."));
    }
}