package biz.phanithnhoem.api.product.web;

import biz.phanithnhoem.api.product.Category;
import biz.phanithnhoem.api.shop.Shop;
import biz.phanithnhoem.api.shop.web.ShopDto;

import java.time.Instant;
import java.util.Set;

public record ProductDto(
        String uuid,
        String code,
        String name,
        String slug,
        String description,
        String image,
        Double price,
        Boolean isDeleted,
        Boolean inStock,
        Integer availableQty,
        Double discountPercent,
        Instant createdAt,
        ShopDto shop,
        Set<CategoryDto> categories
) {
}
