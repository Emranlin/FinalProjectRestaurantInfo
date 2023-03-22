package peaksoft.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MenuItemRequest(
         String name,
         String image,
         BigDecimal price,
         String description,
         Boolean isVegetarian,
         Long restaurantId,
         String subCategoryName
) {
}
