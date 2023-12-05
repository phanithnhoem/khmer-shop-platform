package biz.phanithnhoem.api.user.web;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record UpdateUserDto(String fullName,
                            String username,
                            String email) {
}
