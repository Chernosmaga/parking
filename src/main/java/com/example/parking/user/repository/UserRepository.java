package com.example.parking.user.repository;

import com.example.parking.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneOrEmail(String phone, String email);

    Optional<User> findByPhone(String phone);

    Boolean existsByPhone(String phone);

    Boolean existsByEmail(String email);

    User findByPhoneAndNameAndSurname(String phone, String name, String surname);
}
