package org.example.mediqback.review.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.mediqback.user.model.AuthUserDetails;

public class ReviewDto {
    @Getter
    @Builder
    public static class ReviewReq {
        private String contents;

        public Review toEntity(AuthUserDetails user, Long reviewIdx) {
            return Review.builder()
                    .contents(contents)
                    .review(Review.builder().idx(reviewIdx).build())
                    .user(user.toEntity())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class ReviewRes {
        private Long idx;
        private String contents;
        private String writer;

        public static ReviewDto.ReviewRes from(Review entity) {
            return ReviewRes.builder()
                    .idx(entity.getIdx())
                    .contents(entity.getContents())
                    .writer(entity.getUser().getName())
                    .build();
        }
    }
}
