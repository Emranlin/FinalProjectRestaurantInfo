package peaksoft.dto.response.menuItem;


import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record MenuItemResponseSearch(
        String categoryName,
        String subCategoryName,
        String name,
        String image,
        BigDecimal price
) {

}
