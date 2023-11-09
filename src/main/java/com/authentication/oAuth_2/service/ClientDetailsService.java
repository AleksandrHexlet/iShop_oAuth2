package com.authentication.oAuth_2.service;


import com.authentication.oAuth_2.helper.entity.ClientLoginData;
import com.authentication.oAuth_2.helper.repository.ClientLoginDataRepository;
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
    private ClientLoginDataRepository clientLoginDataRepository;

 @Autowired
    public ClientDetailsService(RegisteredClientRepository registeredClientRepository, ClientLoginDataRepository clientLoginDataRepository) {
        this.registeredClientRepository = registeredClientRepository;
        this.clientLoginDataRepository = clientLoginDataRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username -- " +username);
        RegisteredClient registeredClient = registeredClientRepository.findById(username);
        ClientLoginData clientLoginData = clientLoginDataRepository.findByClientName(username);
        System.out.println("registeredClient.getClientName() == " + registeredClient.getClientName());
        ClientDetails clientDetails = new ClientDetails(registeredClient, clientLoginData);
        System.out.println("clientLoginData === " + clientLoginData.getClientName());
        return clientDetails;
    }
}
