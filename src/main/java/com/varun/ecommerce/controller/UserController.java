package com.varun.ecommerce.controller;


import com.varun.ecommerce.constants.Messages;
import com.varun.ecommerce.dto.UserDto;
import com.varun.ecommerce.model.User;
import com.varun.ecommerce.request.CreateUserRequest;
import com.varun.ecommerce.request.UpdateUserRequest;
import com.varun.ecommerce.response.ApiResponse;
import com.varun.ecommerce.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;

    @GetMapping("/{id}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id){
        try {
            User user = userService.getUserById(id);
            UserDto userDto=userService.convertToUserDto(user);
            return ResponseEntity.ok(new ApiResponse(Messages.SUCCESS.getValue(), userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found",null));
        }
    }

    @PostMapping("/add/user")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertToUserDto(user);
            return ResponseEntity.ok(new ApiResponse(Messages.SUCCESS.getValue(), userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("User Already present",null));
        }
    }

    @PutMapping("/update/{userId}/user")
    public ResponseEntity<ApiResponse> updateUser(@RequestParam UpdateUserRequest request,@PathVariable Long userId){
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertToUserDto(user);
            return ResponseEntity.ok(new ApiResponse(Messages.SUCCESS.getValue(), userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found",null));
        }
    }

    @DeleteMapping("/delete/{userId}/user")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User deleted",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured!",null));
        }
    }

}
