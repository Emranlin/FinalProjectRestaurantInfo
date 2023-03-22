package peaksoft.dto.request;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record StopListRequest(
        String reason,
        LocalDate date,
        Long menuItemId
) {
}
