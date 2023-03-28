package peaksoft.dto.response.restaurant;

import lombok.Builder;

@Builder
public record RestaurantResponseGetById(
        Long id,
        String name,
        String location,
        String restType,
        int numberOfEmployee,
        int service
) {
}
