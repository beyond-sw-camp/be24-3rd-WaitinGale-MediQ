package org.example.mediqback.review;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.review.model.Review;
import org.example.mediqback.review.model.ReviewDto;
import org.example.mediqback.user.model.AuthUserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewDto.ReviewRes reg(AuthUserDetails user, Long reviewIdx, ReviewDto.ReviewReq dto) {
        Review review = reviewRepository.save(dto.toEntity(user, reviewIdx));

        return ReviewDto.ReviewRes.from(review);
    }
}
