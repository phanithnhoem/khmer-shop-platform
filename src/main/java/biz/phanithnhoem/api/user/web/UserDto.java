package biz.phanithnhoem.api.user.web;

import java.time.Instant;
import java.time.LocalDateTime;

public record UserDto(
        String uuid,
        String fullName,
        String username,
        String email,
        Instant createdAt
) {
}
