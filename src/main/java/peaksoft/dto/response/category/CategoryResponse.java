package peaksoft.dto.response.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse{
    private String name;
    private String categoryName;
}
