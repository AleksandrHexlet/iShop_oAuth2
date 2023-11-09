package com.authentication.oAuth_2.helper.repository;

import com.authentication.oAuth_2.helper.entity.ClientLoginData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ClientLoginDataRepository extends JpaRepository <ClientLoginData, Integer> {

    ClientLoginData findByClientName(String clientName);

}
