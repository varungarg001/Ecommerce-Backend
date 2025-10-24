package com.varun.ecommerce.service.user;

import com.varun.ecommerce.dto.UserDto;
import com.varun.ecommerce.model.User;
import com.varun.ecommerce.request.CreateUserRequest;
import com.varun.ecommerce.request.UpdateUserRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertToUserDto(User user);
}
