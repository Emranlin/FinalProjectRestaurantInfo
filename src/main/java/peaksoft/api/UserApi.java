package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.UserLoginRequest;

import peaksoft.dto.request.UserRequestAssign;
import peaksoft.dto.request.UserRequestSave;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.pagination.PaginationResponse;
import peaksoft.dto.response.user.UserResponse;
import peaksoft.dto.response.user.UserResponseGetAll;
import peaksoft.dto.response.user.UserResponseGetById;
import peaksoft.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApi {
    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }


    @PermitAll
    @PostMapping("/login")
    public UserResponse Login(@RequestBody UserLoginRequest loginRequest) {
        return userService.authenticate(loginRequest);
    }

    @PostMapping
    @PermitAll
    public SimpleResponse saveUser(@Valid @RequestBody UserRequestSave userRequestSave,
                                   @RequestParam(required = false) Long restaurantId) {
        return userService.saveUser(userRequestSave,restaurantId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<UserResponseGetAll> getAllCategory() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public UserResponseGetById getById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}")
    public SimpleResponse deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{userId}")
    public SimpleResponse updateUser(@Valid @PathVariable Long userId, @RequestBody UserRequestSave userRequestSave) {
        return userService.updateUser(userId, userRequestSave);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/assign/{userId}")
    public SimpleResponse assignUser(@PathVariable Long userId, @RequestBody UserRequestAssign requestAssign) {
        return userService.assignUser(userId, requestAssign);
    }
    @GetMapping("/pagination")
    public PaginationResponse getUserPagination(@RequestParam (defaultValue = "1") int page, @RequestParam (defaultValue = "10") int size){
        return userService.getUserPagination(page,size);
    }

}
