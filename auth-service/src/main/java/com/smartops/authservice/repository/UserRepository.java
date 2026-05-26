package com.smartops.authservice.repository;

import com.smartops.authservice.entity.User;
// Imports the base repository interface
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// The interface extends JpaRepository, which provides built-in CRUD operations
// Specify <User, Long> because we are managing the 'User' entity with a 'Long' primary key
public interface UserRepository extends JpaRepository<User, Long> {
    //Look for a record in the 'users' table where the 'username' column matches the input
    Optional<User> findByUsername(String username);
}