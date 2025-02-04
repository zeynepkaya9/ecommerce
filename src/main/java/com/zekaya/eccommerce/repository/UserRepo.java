package com.zekaya.eccommerce.repository;

import com.zekaya.eccommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
