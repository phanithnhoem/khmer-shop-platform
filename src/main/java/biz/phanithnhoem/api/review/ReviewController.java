package biz.phanithnhoem.api.review;

import biz.phanithnhoem.api.review.web.CreateReviewDto;
import biz.phanithnhoem.api.review.web.ReviewDto;
import biz.phanithnhoem.base.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ApiResponse<Object> addReview(
            @RequestParam String userUuid,
            @RequestParam String productUuid,
            @RequestBody CreateReviewDto createReviewDto
            ) {
        reviewService.addReview(userUuid, productUuid, createReviewDto);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.CREATED.value())
                .message("Review has been created successfully.")
                .data(createReviewDto)
                .timestamp(Instant.now())
                .build();
    }

    @GetMapping("/{productUuid}")
    public ApiResponse<Object> findReviewByProductUuid(@PathVariable String productUuid) {
        List<ReviewDto> reviewDtoList = reviewService.findReviewByProduct(productUuid);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message("Review has been found.")
                .data(reviewDtoList)
                .timestamp(Instant.now())
                .build();
    }
}
