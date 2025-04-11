package com.smhrd.olaPJ.repository;

import com.smhrd.olaPJ.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findByUsername(String username);

    Optional<User> findByUserId(String userId);


}