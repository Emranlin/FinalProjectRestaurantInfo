package peaksoft.dto.response.menuItem;

import lombok.Builder;

@Builder
public record MenuItemResponseFilter(
        String name,
        Boolean IsVegetarian
) {
}
