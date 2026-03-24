
package org.example.mediqback.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static org.example.mediqback.common.model.BaseResponseStatus.SUCCESS;

@Schema(description = "API 공통 응답 형식") // 추가
@Getter
@Setter
@AllArgsConstructor
public class BaseResponse<T> {

    @Schema(description = "요청 성공 여부", example = "true")
    private Boolean success;

    @Schema(description = "응답 상태 코드", example = "2000")
    private Integer code;

    @Schema(description = "응답 메시지", example = "요청이 성공했습니다")
    private String message;

    @Schema(description = "실제 응답 데이터")
    private T result;

    public static <T> BaseResponse success(T result) {
        return new BaseResponse(
                SUCCESS.isSuccess(),
                SUCCESS.getCode(),
                SUCCESS.getMessage(),
                result
        );
    }

    public static <T> BaseResponse fail(BaseResponseStatus status) {
        return new BaseResponse(
                status.isSuccess(),
                status.getCode(),
                status.getMessage(),
                null
        );
    }

    public static <T> BaseResponse fail(BaseResponseStatus status, T result) {
        return new BaseResponse(
                status.isSuccess(),
                status.getCode(),
                status.getMessage(),
                result
        );
    }
}
