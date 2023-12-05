package biz.phanithnhoem.api.review.web;

import biz.phanithnhoem.api.product.Product;
import biz.phanithnhoem.api.product.web.ProductDto;
import biz.phanithnhoem.api.user.User;
import biz.phanithnhoem.api.user.web.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

public record ReviewDto(
        int rating,
        String comment,
        Instant reviewedDate,
        UserDto user
) {
}
