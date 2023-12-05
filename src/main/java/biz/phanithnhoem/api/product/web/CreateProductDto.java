package biz.phanithnhoem.api.product.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record CreateProductDto (
        @NotBlank
        String name,
        String description,
        @NotNull
        Double price,
        @NotNull
        Integer availableQty,
        Double discountPercent,
        @NotNull
        Integer shopId,
        @NotNull
        Set<@Positive Integer> categoryIds
) {
}
