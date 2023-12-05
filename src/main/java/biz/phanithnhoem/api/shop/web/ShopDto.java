package biz.phanithnhoem.api.shop.web;

import java.time.Instant;

public record ShopDto(
        String uuid,
        String name,
        String email,
        String phone,
        String description,
        String location,
        Instant createdAt

) {
}
