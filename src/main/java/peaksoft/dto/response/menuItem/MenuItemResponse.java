package peaksoft.dto.response.menuItem;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MenuItemResponse(
        String name,
        String image,
        BigDecimal price,
        String description,
        Boolean isVegetarian
) {
}
