package com.authentication.oAuth_2.service;


import com.authentication.oAuth_2.helper.repository.RegisteredClientRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;


@Service
public class ClientDetailsService implements UserDetailsService {
    private RegisteredClientRepository registeredClientRepository;


    @Autowired
    public ClientDetailsService(RegisteredClientRepository registeredClientRepository) {

        this.registeredClientRepository = registeredClientRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisteredClient registeredClient = registeredClientRepository.findById(username);
        System.out.println("registeredClient.getClientName() == " + registeredClient.getClientName());
        ClientDetails clientDetails = new ClientDetails(registeredClient);
        return clientDetails;
    }
}

//    Реализовать класс, имплементирующий UserDetailsService. В методе loadByUsername реализовать
//    логику извлечения информации об аутентифицируемом клиенте из БД и создание
//    экземпляра ClientDetails.