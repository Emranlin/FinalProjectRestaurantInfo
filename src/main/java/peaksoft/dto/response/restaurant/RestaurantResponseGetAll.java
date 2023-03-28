package peaksoft.dto.response.restaurant;

import lombok.Builder;

@Builder
public record RestaurantResponseGetAll(
        Long id,
        String name,
        String location,
        String restType
) {
}
