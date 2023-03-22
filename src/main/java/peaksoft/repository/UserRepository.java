package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.user.UserResponseGetAll;
import peaksoft.dto.response.user.UserResponseGetById;
import peaksoft.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


      Boolean existsByEmail(String email);


    @Query("select new peaksoft.dto.response.user.UserResponseGetAll(u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.experience)from User u where u.restaurant.id <>null")
    List<UserResponseGetAll> getAllUser();
    Optional<UserResponseGetById>getUserById(Long userId);



}