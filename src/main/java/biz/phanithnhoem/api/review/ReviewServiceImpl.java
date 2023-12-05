package biz.phanithnhoem.api.review;

import biz.phanithnhoem.api.product.Product;
import biz.phanithnhoem.api.product.ProductRepository;
import biz.phanithnhoem.api.review.web.CreateReviewDto;
import biz.phanithnhoem.api.review.web.ReviewDto;
import biz.phanithnhoem.api.user.User;
import biz.phanithnhoem.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    @Override
    public void addReview(String userUuid, String productUuid, CreateReviewDto createReviewDto) {

        // Find a user in the repository based on the UUID, ensuring that the user is not marked as deleted
        User user = userRepository.findByUuidAndIsDeletedFalse(userUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The requested user was not found in the database."));

        // Find a product in the repository based on the product UUID
        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The requested product was not found in the database."));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReviewedDate(Instant.now());
        review.setComment(createReviewDto.comment());
        review.setRating(createReviewDto.rating());

        // Save the review to the database using the review repository
        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDto> findReviewByProduct(String productUuid) {
        // Find a product in the repository based on the product UUID
        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The requested product was not found in the database."));
        List<Review> reviews = reviewRepository.findAllByProduct(product);
        return reviewMapper.toReviewDtoList(reviews);
    }
}
