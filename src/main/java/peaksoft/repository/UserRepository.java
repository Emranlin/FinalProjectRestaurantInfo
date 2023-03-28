package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.user.UserResponse;
import peaksoft.dto.response.user.UserResponseGetAll;
import peaksoft.dto.response.user.UserResponseGetById;
import peaksoft.entity.User;
import peaksoft.enums.Role;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);


    Boolean existsByEmail(String email);
    Boolean existsByRole(Role role);


    @Query("select new peaksoft.dto.response.user.UserResponseGetAll(u.id,u.firstName,u.lastName,u.phoneNumber ) from User u ")
    List<UserResponseGetAll> getAllUser();

    Optional<UserResponseGetById>getUserById(Long userId);

    Page<UserResponseGetAll> findAllProjectedBy(Pageable pageable);
}