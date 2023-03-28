package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.restaurant.RestaurantResponse;
import peaksoft.dto.response.restaurant.RestaurantResponseGetAll;
import peaksoft.dto.response.restaurant.RestaurantResponseGetById;
import peaksoft.entity.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select new peaksoft.dto.response.restaurant.RestaurantResponseGetAll(r.id,r.name,r.location,r.restType)from Restaurant  r ")
    List<RestaurantResponseGetAll> getAllRestaurant();
    @Query("select new peaksoft.dto.response.restaurant.RestaurantResponseGetById(r.id,r.name,r.location,r.restType,r.numberOfEmployee,r.service)from Restaurant  r where r.id=:id ")
    Optional<RestaurantResponseGetById> getRestaurantById( Long id);
    Boolean existsByName(String name);


}
