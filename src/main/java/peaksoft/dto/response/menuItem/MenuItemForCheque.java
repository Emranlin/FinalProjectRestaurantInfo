package peaksoft.dto.response.menuItem;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record MenuItemForCheque(
        Long id,
        String name,
        BigDecimal price,
        Long count
) {
}
