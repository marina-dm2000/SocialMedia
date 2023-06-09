package com.example.socialMedia.repository;

import com.example.socialMedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);

    Optional<User> findByUserName(String userName);

    Optional<User> findById(Long id);

    User findUserByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
