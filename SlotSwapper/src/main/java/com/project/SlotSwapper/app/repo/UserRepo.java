package com.project.SlotSwapper.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.SlotSwapper.app.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
    