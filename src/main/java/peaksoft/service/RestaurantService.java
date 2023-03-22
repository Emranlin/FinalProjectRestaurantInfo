package peaksoft.service;

import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.restaurant.RestaurantResponseGetAll;
import peaksoft.dto.response.restaurant.RestaurantResponseGetById;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface RestaurantService {

    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);

    List<RestaurantResponseGetAll> getAllRestaurant();
    RestaurantResponseGetById getByIdRestaurant(Long restaurantId);
    SimpleResponse updateRestaurant(Long restaurantId,RestaurantRequest restaurantRequest);
    SimpleResponse deleteRestaurant(Long restaurantId);

}
