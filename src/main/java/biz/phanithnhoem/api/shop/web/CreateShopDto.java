package biz.phanithnhoem.api.shop.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record CreateShopDto(
        @NotBlank
        String name,
        String description,
        @NotBlank
        String location,
        @NotBlank
        String email,
        @NotBlank
        String phone,
        @Positive
        @NotNull
        Integer userId

) {
}
