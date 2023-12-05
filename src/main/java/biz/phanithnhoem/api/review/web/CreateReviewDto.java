package biz.phanithnhoem.api.review.web;

import jakarta.validation.constraints.Size;

public record CreateReviewDto (
        @Size(max = 10)
        int rating,
        String comment
) {
}
