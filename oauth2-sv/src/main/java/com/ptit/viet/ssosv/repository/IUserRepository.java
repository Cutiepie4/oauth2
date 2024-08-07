package com.ptit.viet.ssosv.repository;

import com.ptit.viet.ssosv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
