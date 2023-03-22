package peaksoft.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.restaurant.RestaurantResponseGetAll;
import peaksoft.dto.response.restaurant.RestaurantResponseGetById;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantApi {
    private  final RestaurantService restaurantService;

    public RestaurantApi(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    @PreAuthorize("permitAll()")
    @PostMapping
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.saveRestaurant(restaurantRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<RestaurantResponseGetAll> getAllRestaurant(){
        return restaurantService.getAllRestaurant();
    }

    @GetMapping("/{restaurantId}")
    public RestaurantResponseGetById getById(@PathVariable Long restaurantId){
        return restaurantService.getByIdRestaurant(restaurantId);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping ("/{restaurantId}")
    public SimpleResponse updateRestaurant(@PathVariable Long restaurantId,@RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.updateRestaurant(restaurantId,restaurantRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{restaurantId}")
    public SimpleResponse deleteRestaurant(@PathVariable Long restaurantId){
        return restaurantService.deleteRestaurant(restaurantId);
    }

}
