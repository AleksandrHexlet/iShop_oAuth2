package com.authentication.oAuth_2.helper.repository;


import com.authentication.oAuth_2.helper.entity.ClientApp;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientAppRepository extends CrudRepository <ClientApp, Integer> {

    Optional<ClientApp> findByUserName(String userName);
}
