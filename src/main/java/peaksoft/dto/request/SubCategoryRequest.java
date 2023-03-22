package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record SubCategoryRequest(
        String name,
        Long categoryId
) {
}
