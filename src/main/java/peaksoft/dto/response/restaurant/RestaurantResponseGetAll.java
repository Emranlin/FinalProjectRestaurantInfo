package peaksoft.dto.response.restaurant;

import lombok.Builder;

@Builder
public record RestaurantResponseGetAll(
        String name,
        String location,
        String restType
) {
}
