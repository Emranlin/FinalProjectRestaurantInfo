package peaksoft.dto.request;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ChequeRequest(
        List<Long> menuItemId
) {
}
