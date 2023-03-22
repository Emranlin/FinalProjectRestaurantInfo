package peaksoft.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.restaurant.RestaurantResponseGetAll;
import peaksoft.dto.response.restaurant.RestaurantResponseGetById;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Restaurant;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.service.RestaurantService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setService(restaurantRequest.service());
        restaurantRepository.save(restaurant);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("The restaurant %s successful saved", restaurantRequest.name())).build();
    }

    @Override
    public List<RestaurantResponseGetAll> getAllRestaurant() {
        return restaurantRepository.getAllRestaurant();
    }

    @Override
    public RestaurantResponseGetById getByIdRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException(String.format("Restaurant with id:%s not found", restaurantId)));
        if (restaurant.getNumberOfEmployee() <= 15) {
            System.out.println("The isn't vacancy ");
        } else {
            restaurant.setNumberOfEmployee(restaurant.getUsers().size());

            restaurantRepository.save(restaurant);

            return restaurantRepository.getRestaurantById(restaurantId).orElseThrow(() -> new NoSuchElementException(String.format("Restaurant with id:%s not found", restaurantId)));
        }
        return null;
    }

    @Override
    public SimpleResponse updateRestaurant(Long restaurantId, RestaurantRequest restaurantRequest) {
        Restaurant updateRestaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Not found restaurant"));
        updateRestaurant.setLocation(restaurantRequest.location());
        updateRestaurant.setName(restaurantRequest.name());
        updateRestaurant.setRestType(restaurantRequest.restType());
        updateRestaurant.setService(restaurantRequest.service());
        restaurantRepository.save(updateRestaurant);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Restaurant with id: %s successful updated")).build();

    }

    @Override
    public SimpleResponse deleteRestaurant(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message(String.format("Restaurant with id: %s not found!", restaurantId))
                    .build();
        }
            restaurantRepository.deleteById(restaurantId);
            return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Restaurant with id: %s success deleted",restaurantId)).build();
        }


    }

