package peaksoft.dto.response.menuItem;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MenuItemResponseForCheque(
        String name,
        BigDecimal price
) {
}
