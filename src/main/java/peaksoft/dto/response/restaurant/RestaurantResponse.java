package peaksoft.dto.response.restaurant;

import lombok.*;

@Builder
public record RestaurantResponse (Long id,
                                  String name,
                                  String location,
                                  String restType,
                                  int numberOfEmployee,
                                  int service){

}
