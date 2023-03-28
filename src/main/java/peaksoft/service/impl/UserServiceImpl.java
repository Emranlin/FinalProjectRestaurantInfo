package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.config.jwt.JwtUtil;
import peaksoft.dto.request.UserLoginRequest;
import peaksoft.dto.request.UserRequestAssign;
import peaksoft.dto.request.UserRequestSave;
import peaksoft.dto.response.*;
import peaksoft.dto.response.pagination.PaginationResponse;
import peaksoft.dto.response.user.UserResponse;
import peaksoft.dto.response.user.UserResponseGetAll;
import peaksoft.dto.response.user.UserResponseGetById;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.AlreadyExistsException;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl  implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RestaurantRepository restaurantRepository;

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
    private void checkInFo(UserRequestSave userRequestSave) {
        if (userRepository.existsByEmail(userRequestSave.email())) {
            throw new AlreadyExistsException("User with email: %s already exists!");

        }
        if (userRepository.existsByPhoneNumber(userRequestSave.phoneNumber())) {
            throw new AlreadyExistsException("User with phoneNumber: %s already exists!");

        }
        if (userRequestSave.role().equals(Role.CHEF)) {
            LocalDate currentDay;
            currentDay = LocalDate.now();
            int age;
            age = (int) ChronoUnit.YEARS.between(userRequestSave.dateOfBirth(), currentDay);

            if (age < 25 || age > 45) {
                throw new BadRequestException("Age of chef must be between 25 and 45 years old");

            }
            if (userRequestSave.experience() >= 1) {
                throw new BadRequestException("Cooking experience must be at least 2 years!");

            }
        } else if (userRequestSave.role().equals(Role.WAITER)) {
            long age = ChronoUnit.YEARS.between(userRequestSave.dateOfBirth(), LocalDate.now());
            if (age < 18 || 30 < age) {
                throw new BadRequestException("Age for waiter should be between 18 and 25!");

            }
            if (userRequestSave.experience() >= 1) {
                throw  new AlreadyExistsException("Experience of waiter must be least 1 year!");

            }
        } else {
            if (userRepository.existsByRole(Role.ADMIN)) {
                throw new AlreadyExistsException("An admin user already exists");

            }
        }

    }


    @Override
    @Transactional
    public SimpleResponse saveUser(UserRequestSave userRequestSave, Long restaurantId) {
       checkInFo(userRequestSave);

            User user1 = new User();
            user1.setFirstName(userRequestSave.firstName());
            user1.setLastName(userRequestSave.lastName());
            user1.setDateOfBirth(userRequestSave.dateOfBirth());
            user1.setExperience(userRequestSave.experience());
            user1.setEmail(userRequestSave.email());
            user1.setPhoneNumber(userRequestSave.phoneNumber());
            user1.setPassword(passwordEncoder.encode(userRequestSave.password()));
            user1.setRole(userRequestSave.role());

            if (restaurantId != null){
                Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException(String.format("Restaurant with id: %s not found", restaurantId)));
                user1.setRestaurant(restaurant);
                restaurant.addUser(user1);
            }
            userRepository.save(user1);

        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Success saved").build();
    }

    @Override
    public SimpleResponse assignUser(Long userId, UserRequestAssign requestAssign) {
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
        } else {
            return SimpleResponse.builder().httpStatus(HttpStatus.EXPECTATION_FAILED).message("Invalid assign").build();
        }
    }

    @Override
    public PaginationResponse getUserPagination(int page, int size) {

        Pageable pageable =PageRequest.of(page-1, size);
        Page<User> pagedUsers = userRepository.findAll(pageable);

        List<UserResponseGetAll> userResponses = pagedUsers.getContent().stream()
                .map(user -> new UserResponseGetAll(user.getId(), user.getFirstName(), user.getLastName(),user.getPhoneNumber()))
                .collect(Collectors.toList());
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setUserResponseGetAllList(userResponses);
        paginationResponse.setCurrentPage(pagedUsers.getNumber() + 1);
        paginationResponse.setPageSize(pagedUsers.getSize());

        return paginationResponse;
    }


    @Override
    public List<UserResponseGetAll> getAllUsers(){

        return userRepository.getAllUser();
    }

    @Override
    public UserResponseGetById getById(Long userId) {
        return userRepository.getUserById(userId).orElseThrow(()->new NotFoundException(String.format("User with id:%s not found",userId)));
    }

    @Override
    public SimpleResponse updateUser(Long userId, UserRequestSave userRequestSave) {
       checkInFo(userRequestSave);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User by this id: %s not found", userId)));
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
