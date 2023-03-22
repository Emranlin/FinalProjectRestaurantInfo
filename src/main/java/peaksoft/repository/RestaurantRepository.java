package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.restaurant.RestaurantResponseGetAll;
import peaksoft.dto.response.restaurant.RestaurantResponseGetById;
import peaksoft.entity.Restaurant;

import java.util.List;
import java.util.Optional;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select new peaksoft.dto.response.restaurant.RestaurantResponseGetAll(r.name,r.location,r.restType)from Restaurant  r ")
    List<RestaurantResponseGetAll> getAllRestaurant();
    Optional<RestaurantResponseGetById> getRestaurantById( Long id);

}
