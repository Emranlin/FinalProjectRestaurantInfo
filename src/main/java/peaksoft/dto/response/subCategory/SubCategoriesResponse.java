package peaksoft.dto.response.subCategory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubCategoriesResponse {
    @JsonIgnore
    private String categoryName;
    private String name;

}
