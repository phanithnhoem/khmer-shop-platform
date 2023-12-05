package biz.phanithnhoem.base;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiPagedResponse<T>(
        String status,
        Integer code,
        String message,
        T data,
        Integer pageNo,
        Integer recordCount,
        Long totalRecords,
        Long totalPages,
        Instant timestamp
) {
}
