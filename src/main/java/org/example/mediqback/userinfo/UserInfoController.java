package org.example.mediqback.userinfo;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.user.model.AuthUserDetails;
import org.example.mediqback.userinfo.model.UserInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage/info")
@RequiredArgsConstructor
@CrossOrigin
public class UserInfoController {

    private final UserInfoService userInfoService;

    // 정보 조회 API
    @GetMapping
    public ResponseEntity<BaseResponse<UserInfoDto>> getInfo(@AuthenticationPrincipal AuthUserDetails userDetails) {
        // AuthUserDetails의 username은 이메일을 의미합니다.
        UserInfoDto userInfo = userInfoService.getUserInfo(userDetails.getUsername());
        return ResponseEntity.ok(BaseResponse.success(userInfo));
    }

    // 정보 수정 API
    @PostMapping
    public ResponseEntity<BaseResponse<String>> updateInfo(
            @AuthenticationPrincipal AuthUserDetails userDetails,
            @RequestBody UserInfoDto requestDto) {

        userInfoService.updateUserInfo(userDetails.getUsername(), requestDto);
        return ResponseEntity.ok(BaseResponse.success("의료 및 개인 정보가 성공적으로 업데이트되었습니다."));
    }
}