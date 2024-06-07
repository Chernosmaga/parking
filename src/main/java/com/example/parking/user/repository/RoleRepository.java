package com.example.parking.user.repository;

import com.example.parking.enums.RoleName;
import com.example.parking.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName name);
}
