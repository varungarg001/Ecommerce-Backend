package com.varun.ecommerce.service.user;

import com.varun.ecommerce.exception.AlreadyExistsException;
import com.varun.ecommerce.exception.UserNotFound;
import com.varun.ecommerce.model.User;
import com.varun.ecommerce.repository.UserRepo;
import com.varun.ecommerce.request.CreateUserRequest;
import com.varun.ecommerce.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepo userRepo;

    @Override
    public User getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(()->new UserNotFound("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional
                .of(request)
                .filter(user->!userRepo.existsByEmail(request.getEmail()))
                .map(req->{
                    User user=new User();
                    user.setFirstName(req.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    return userRepo.save(user);
                }).orElseThrow(()->new AlreadyExistsException("OOPS! "+request.getEmail()+" already exists"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepo.findById(userId).map(existingUser->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepo.save(existingUser);
        }).orElseThrow(()->new UserNotFound("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete,()->{
            throw new UserNotFound("user not found");
        });
    }
}
