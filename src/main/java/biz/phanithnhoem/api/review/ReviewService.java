package biz.phanithnhoem.api.review;

import biz.phanithnhoem.api.review.web.CreateReviewDto;
import biz.phanithnhoem.api.review.web.ReviewDto;

import java.util.List;


public interface ReviewService {

    /**
     * Adds a new review for a product using the information provided in the CreateReviewDto object.
     *
     * @param userUuid         The UUID of the user submitting the review.
     * @param productUuid      The UUID of the product being reviewed.
     * @param createReviewDto  The data transfer object containing review details, such as comment and rating.
     */
    void addReview(String userUuid, String productUuid, CreateReviewDto createReviewDto);

    /**
     * Retrieves a list of review DTOs for a specific product.
     *
     * @param productUuid The UUID of the product for which reviews are requested.
     * @return A list of ReviewDto objects representing reviews for the specified product.
     */
    List<ReviewDto> findReviewByProduct(String productUuid);
}
