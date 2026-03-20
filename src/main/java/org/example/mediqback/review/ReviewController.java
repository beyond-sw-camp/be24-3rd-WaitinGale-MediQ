package org.example.mediqback.review;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.common.model.BaseResponse;
import org.example.mediqback.review.model.ReviewDto;
import org.example.mediqback.user.model.AuthUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/reg/{reviewIdx}")
    public ResponseEntity reg (
            @AuthenticationPrincipal AuthUserDetails user,
            @PathVariable Long reviewIdx,
            @RequestBody ReviewDto.ReviewReq dto) {
        ReviewDto.ReviewRes result = reviewService.reg(user,reviewIdx,dto);

        return ResponseEntity.ok(
                BaseResponse.success(result)
        );
    }
}
