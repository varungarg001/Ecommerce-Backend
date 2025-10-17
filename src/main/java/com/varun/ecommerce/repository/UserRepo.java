package com.varun.ecommerce.repository;

import com.varun.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
}
