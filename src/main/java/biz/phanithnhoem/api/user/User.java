package biz.phanithnhoem.api.user;

import biz.phanithnhoem.api.cart.Cart;
import biz.phanithnhoem.api.review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String fullName;
    @Column(nullable = false)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String verifiedCode;
    @Column(length = 10)
    private Boolean isVerified;
    private Boolean isDeleted;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    private Instant updatedAt;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<Review> reviews;
}
