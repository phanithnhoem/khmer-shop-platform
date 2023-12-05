package biz.phanithnhoem.api.review;

import biz.phanithnhoem.api.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByProduct(Product product);
}
