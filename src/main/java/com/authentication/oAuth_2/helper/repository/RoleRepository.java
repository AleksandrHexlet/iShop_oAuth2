package com.authentication.oAuth_2.helper.repository;

import com.authentication.oAuth_2.helper.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleType(Role.RoleType roleType);
}