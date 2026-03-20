package org.example.mediqback.common.exception;

import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.common.model.BaseResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid 검증(정규식 등)에 실패했을 때 발생하는 에러를 낚아챔
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        for(FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse.fail(BaseResponseStatus.REQUEST_ERROR, errors)
        );
    }

    // 우리가 만든 BaseException을 낚아챔
    @ExceptionHandler(BaseException.class)
    public ResponseEntity handleException(BaseException e) {
        BaseResponseStatus status = e.getStatus();
        int errorCode = status.getCode();
        int statusCode = statusCodeMapper(errorCode);
        BaseResponse response = BaseResponse.fail(status);

        return ResponseEntity
                .status(statusCode)
                .body(response);
    }

    private int statusCodeMapper(int errorCode) {
        if (errorCode > 3000) {
            return 400;
        } else if (errorCode >= 5000) {
            return 500;
        }
        return 400;
    }
}