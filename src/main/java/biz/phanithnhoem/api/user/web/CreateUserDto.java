package biz.phanithnhoem.api.user.web;

import jakarta.validation.constraints.*;

import java.util.Set;


public record CreateUserDto(
        @NotBlank(message = "Full Name is required")
        String fullName,
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @NotBlank
        @Size(min = 8, message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one digit")
        String password,
        @NotNull(message = "Roles is required")
        Set<@Positive Integer> roleIds
) {
}
