package peaksoft.service;

import peaksoft.dto.request.UserLoginRequest;
import peaksoft.dto.request.UserRequestAssign;
import peaksoft.dto.request.UserRequestSave;
import peaksoft.dto.response.*;
import peaksoft.dto.response.user.UserResponse;
import peaksoft.dto.response.user.UserResponseGetAll;
import peaksoft.dto.response.user.UserResponseGetById;

import java.util.List;

public interface UserService {
    UserResponse authenticate(UserLoginRequest userRequest);
    SimpleResponse saveUser(UserRequestSave userRequestSave);
    List<UserResponseGetAll> getAllUsers();
    UserResponseGetById getById(Long userId);
    SimpleResponse updateUser(Long userId,UserRequestSave userRequestSave);
    SimpleResponse deleteUser(Long userId);
    SimpleResponse assignUser(Long userId, UserRequestAssign userRequestAssign);

}
