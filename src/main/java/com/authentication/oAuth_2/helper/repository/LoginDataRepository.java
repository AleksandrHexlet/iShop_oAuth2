package com.authentication.oAuth_2.helper.repository;

import com.authentication.oAuth_2.helper.entity.LoginData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginDataRepository extends JpaRepository<LoginData, Integer> {
    Optional<LoginData> findByUserName(String userName);
    public boolean existsByUserName(String userName);

}
