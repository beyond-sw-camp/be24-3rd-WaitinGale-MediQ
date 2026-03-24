package org.example.mediqback.finduserinfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.common.model.BaseResponseStatus;
import org.example.mediqback.finduserinfo.model.FindUserInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "FindUserInfo API", description = "아이디(이메일) 찾기 및 임시 비밀번호 발급 API")
@RestController
@RequestMapping("/api/find")
@RequiredArgsConstructor
@CrossOrigin
public class FindUserInfoController {

    private final FindUserInfoService findUserInfoService;

    // 아이디 찾기 API
    @Operation(summary = "아이디(이메일) 찾기", description = "사용자의 이름을 입력받아 가입된 이메일을 마스킹 처리하여 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "아이디 찾기 성공"),
            @ApiResponse(responseCode = "400", description = "가입된 계정이 없음")
    })
    @PostMapping("/id")
    public ResponseEntity<BaseResponse<String>> findId(@RequestBody FindUserInfoDto.FindIdReq req) {
        try {
            String maskedEmail = findUserInfoService.findEmail(req);
            return ResponseEntity.ok(BaseResponse.success(maskedEmail));
        } catch (IllegalArgumentException e) {
            //  null 대신 BaseResponseStatus.REQUEST_ERROR 사용 (NPE 방지)
            return ResponseEntity.badRequest().body(BaseResponse.fail(BaseResponseStatus.REQUEST_ERROR, e.getMessage()));
        }
    }

    // 임시 비밀번호 발급 API
    @Operation(summary = "임시 비밀번호 발급", description = "가입된 이메일을 입력받아 무작위 임시 비밀번호를 생성하고, 해당 이메일로 발송합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "임시 비밀번호 발송 성공"),
            @ApiResponse(responseCode = "400", description = "가입되지 않은 이메일이거나 소셜 로그인 계정")
    })
    @PostMapping("/password/reset")
    public ResponseEntity<BaseResponse<String>> resetPassword(@RequestBody FindUserInfoDto.FindPwReq req) {
        try {
            findUserInfoService.sendTempPassword(req);
            return ResponseEntity.ok(BaseResponse.success("이메일로 임시 비밀번호가 발송되었습니다."));
        } catch (IllegalArgumentException e) {
            //  null 대신 BaseResponseStatus.REQUEST_ERROR 사용
            return ResponseEntity.badRequest().body(BaseResponse.fail(BaseResponseStatus.REQUEST_ERROR, e.getMessage()));
        }
    }
}