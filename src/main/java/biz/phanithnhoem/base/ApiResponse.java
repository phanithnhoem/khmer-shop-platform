package biz.phanithnhoem.base;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiResponse<T>(String status,
                             Integer code,
                             String message,
                             T data,
                             Instant timestamp) {
}
