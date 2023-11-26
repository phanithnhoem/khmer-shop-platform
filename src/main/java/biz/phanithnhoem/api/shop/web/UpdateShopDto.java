package biz.phanithnhoem.api.shop.web;

import lombok.Builder;

@Builder
public record UpdateShopDto(
        String name,
        String description,
        String email,
        String phone) {
}
