package peaksoft.dto.response.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder

public record CategoryResponse(
        Long id,
     String name
){

}
