package biz.phanithnhoem.api.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameAndShopId(String name, Integer id);
    // Using derive query method  to retrieve product
    Optional<Product> findByUuid(String uuid);
}
