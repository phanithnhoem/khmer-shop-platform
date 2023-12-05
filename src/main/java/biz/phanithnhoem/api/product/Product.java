package biz.phanithnhoem.api.product;

import biz.phanithnhoem.api.review.Review;
import biz.phanithnhoem.api.shop.Shop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false, unique = true)
    private String uuid;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    private String slug;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    private Double price;
    private Boolean isDeleted;
    private Boolean inStock;
    private Integer availableQty;
    private Double discountPercent;
    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "products_categories",
        joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviews;
}
