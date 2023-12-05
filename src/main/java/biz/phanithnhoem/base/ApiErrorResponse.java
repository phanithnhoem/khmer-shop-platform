package biz.phanithnhoem.base;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiErrorResponse<T> (Boolean status,
                                   Integer code,
                                   String message,
                                   T errors,
                                   Instant timestamp) {
}
