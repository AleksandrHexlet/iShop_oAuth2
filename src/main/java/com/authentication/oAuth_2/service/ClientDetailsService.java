package com.authentication.oAuth_2.service;


import com.authentication.oAuth_2.helper.entity.ClientApp;
import com.authentication.oAuth_2.helper.entity.LoginData;
import com.authentication.oAuth_2.helper.entity.Role;
import com.authentication.oAuth_2.helper.repository.ClientAppRepository;
import com.authentication.oAuth_2.helper.repository.LoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;


@Service
public class ClientDetailsService implements UserDetailsService {
    private RegisteredClientRepository registeredClientRepository;
    private ClientAppRepository clientAppRepository;

    @Autowired
    public ClientDetailsService(RegisteredClientRepository registeredClientRepository, ClientAppRepository clientAppRepository) {
        this.registeredClientRepository = registeredClientRepository;
        this.clientAppRepository = clientAppRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username -- " +username);
        RegisteredClient registeredClient = registeredClientRepository.findById(username);
        ClientApp clientApp = clientAppRepository.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("Такой клиент не зарегистрирован"));
        if (registeredClient == null) throw new UsernameNotFoundException("Такой клиент не зарегистрирован");
        if (clientApp.getRole().getRoleType() != Role.RoleType.ROLE_CLIENT) throw new UsernameNotFoundException("Такой клиент не зарегистрирован");
        System.out.println("registeredClient.getClientName() == " + registeredClient.getClientName());
        ClientDetails clientDetails = new ClientDetails(registeredClient, clientApp);
        System.out.println("clientLoginData === " + clientApp.getUserName());
        return clientDetails;
    }
}
