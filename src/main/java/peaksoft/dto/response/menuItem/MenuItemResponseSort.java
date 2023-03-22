package peaksoft.dto.response.menuItem;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record MenuItemResponseSort(
        String name,
        BigDecimal price
) {
}
