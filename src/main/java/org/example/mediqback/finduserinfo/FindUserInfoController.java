package org.example.mediqback.finduserinfo;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.common.model.BaseResponseStatus;
import org.example.mediqback.finduserinfo.model.FindUserInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/find")
@RequiredArgsConstructor
@CrossOrigin
public class FindUserInfoController {

    private final FindUserInfoService findUserInfoService;

    // 아이디 찾기 API
    @PostMapping("/id")
    public ResponseEntity<BaseResponse<String>> findId(@RequestBody FindUserInfoDto.FindIdReq req) {
        try {
            String maskedEmail = findUserInfoService.findEmail(req);
            return ResponseEntity.ok(BaseResponse.success(maskedEmail));
        } catch (IllegalArgumentException e) {
            // 💡 null 대신 BaseResponseStatus.REQUEST_ERROR 사용 (NPE 방지)
            return ResponseEntity.badRequest().body(BaseResponse.fail(BaseResponseStatus.REQUEST_ERROR, e.getMessage()));
        }
    }

    // 임시 비밀번호 발급 API
    @PostMapping("/password/reset")
    public ResponseEntity<BaseResponse<String>> resetPassword(@RequestBody FindUserInfoDto.FindPwReq req) {
        try {
            findUserInfoService.sendTempPassword(req);
            return ResponseEntity.ok(BaseResponse.success("이메일로 임시 비밀번호가 발송되었습니다."));
        } catch (IllegalArgumentException e) {
            // 💡 null 대신 BaseResponseStatus.REQUEST_ERROR 사용
            return ResponseEntity.badRequest().body(BaseResponse.fail(BaseResponseStatus.REQUEST_ERROR, e.getMessage()));
        }
    }
}