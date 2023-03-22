package peaksoft.dto.response.stopList;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record StopListResponse(
        String menuItemName,
        String reason,
        LocalDate date
) {
}
