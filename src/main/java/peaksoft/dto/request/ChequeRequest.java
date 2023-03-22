package peaksoft.dto.request;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record ChequeRequest(
         LocalDate createdAt,
         Long userId
) {
}
