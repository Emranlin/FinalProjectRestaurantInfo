package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtUtil;
import peaksoft.dto.request.UserLoginRequest;
import peaksoft.dto.request.UserRequestAssign;
import peaksoft.dto.request.UserRequestSave;
import peaksoft.dto.response.*;
import peaksoft.dto.response.user.UserResponse;
import peaksoft.dto.response.user.UserResponseGetAll;
import peaksoft.dto.response.user.UserResponseGetById;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class UserServiceImpl  implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private  final RestaurantRepository restaurantRepository;

 @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;

        this.restaurantRepository = restaurantRepository;
    }
    @Override
    public UserResponse authenticate(UserLoginRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.email(),
                        userRequest.password()
                )
        );

         User user = userRepository.findByEmail(userRequest.email())
                .orElseThrow(() -> new NoSuchElementException(String.format
                        ("User with email: %s doesn't exists", userRequest.email())));
        String token = jwtUtil.generateToken(user);

        return UserResponse.builder()
                .token(token)
                .email(user.getEmail())
                .build();

    }

    @Override
    public SimpleResponse saveUser(UserRequestSave userRequestSave) {
        if (userRepository.existsByEmail(userRequestSave.email())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("User with email: %s already exists!")
                    .build();}
            if (userRequestSave.role().equals(Role.CHEF)) {
                LocalDate currentDay;
                currentDay = LocalDate.now();
                int age;
                age = (int) ChronoUnit.YEARS.between(userRequestSave.dateOfBirth(), currentDay);

                if (age < 25 || age > 45) {
                    return SimpleResponse.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .message("Age of chef must be between 25 and 45 years old")
                            .build();
                }
                if (userRequestSave.experience() >= 1) {
                    return SimpleResponse.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .message("Cooking experience must be at least 2 years!")
                            .build();
                }
            } else if (userRequestSave.role().equals(Role.WAITER)) {
                long age = ChronoUnit.YEARS.between(userRequestSave.dateOfBirth(), LocalDate.now());
                if (age < 18 || 30 < age) {
                    return SimpleResponse.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .message("Age for waiter should be between 18 and 25!")
                            .build();
                }
                if (userRequestSave.experience() >= 1) {
                    return SimpleResponse.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .message("Experience of waiter must be least 1 year!")
                            .build();
                }
            }

        User user=new User();
        user.setFirstName(userRequestSave.firstName());
        user.setLastName(userRequestSave.lastName());
        user.setDateOfBirth(userRequestSave.dateOfBirth());
        user.setExperience(userRequestSave.experience());
        user.setEmail(userRequestSave.email());
        user.setPhoneNumber(userRequestSave.phoneNumber());
        user.setPassword(passwordEncoder.encode(userRequestSave.password()));
        user.setRole(userRequestSave.role());
        userRepository.save(user);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Success saved").build();
    }
    @Override
    public SimpleResponse assignUser(Long userId , UserRequestAssign requestAssign) {
        Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow();
        if (restaurant.getNumberOfEmployee() > 15) {
            return SimpleResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).message("Vacancy closed ").build();
        }

        if (requestAssign.assign().equalsIgnoreCase("assign")) {
            User user1 = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found", userId)));
            restaurant.addUser(user1);
            user1.setRestaurant(restaurant);
            userRepository.save(user1);
            restaurantRepository.save(restaurant);
            return SimpleResponse.builder().httpStatus(HttpStatus.ACCEPTED).message("Success saved").build();
        }else {
            return SimpleResponse.builder().httpStatus(HttpStatus.EXPECTATION_FAILED).message("Invalid assign").build();
        }
    }

    @Override
    public List<UserResponseGetAll> getAllUsers(){

        return userRepository.getAllUser();
    }

    @Override
    public UserResponseGetById getById(Long userId) {
        return userRepository.getUserById(userId).orElseThrow(()->new RuntimeException("not found"));
    }

    @Override
    public SimpleResponse updateUser(Long userId, UserRequestSave userRequestSave) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User by this id: %s not found", userId)));
        if (userRepository.existsByEmail(user.getEmail())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("User with email: %s already exists!")
                    .build();}
        if (userRequestSave.role().equals(Role.CHEF)) {
            LocalDate currentDay;
            currentDay = LocalDate.now();
            int age;
            age = (int) ChronoUnit.YEARS.between(userRequestSave.dateOfBirth(), currentDay);

            if (age < 25 || age > 45) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Age of chef must be between 25 and 45 years old")
                        .build();
            }
            if (userRequestSave.experience() >= 1) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Cooking experience must be at least 2 years!")
                        .build();
            }
        } else if (userRequestSave.role().equals(Role.WAITER)) {
            LocalDate currentDay;
            currentDay = LocalDate.now();
            int age;
            age = (int) ChronoUnit.YEARS.between(userRequestSave.dateOfBirth(), currentDay);
            if (age < 18 || 30 < age) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Age for waiter should be between 18 and 25!")
                        .build();
            }
            if (userRequestSave.experience() >= 1) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Experience of waiter must be least 1 year!")
                        .build();
            }
        }
        user.setFirstName(userRequestSave.firstName());
        user.setLastName(userRequestSave.lastName());
        user.setDateOfBirth(userRequestSave.dateOfBirth());
        user.setExperience(userRequestSave.experience());
        user.setEmail(userRequestSave.email());
        user.setPhoneNumber(userRequestSave.phoneNumber());
        user.setPassword(passwordEncoder.encode(userRequestSave.password()));
        user.setRole(userRequestSave.role());
        userRepository.save(user);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("User by id:%s successfully updated",userId)).build();
    }

    @Override
    public SimpleResponse deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("User with %s id deleted",userId)).build();
    }


    @PostConstruct
    public void saveAdmin() {
        if (!userRepository.existsByEmail("zhijdegul@gmail.com")) {
            User user = new User();
            user.setFirstName("Zhiidegul");
            user.setLastName("Zhalilova");
            user.setEmail("zhijdegul@gmail.com");
            user.setRole(Role.ADMIN);
            user.setPhoneNumber("0509470303");
            String dateString = "1993-01-15";
            LocalDate dateOfBirth = LocalDate.parse(dateString);
            user.setDateOfBirth(dateOfBirth);
            user.setExperience(5);
            user.setPassword(passwordEncoder.encode("zhiide1245"));
            userRepository.save(user);
        }
    }


}
