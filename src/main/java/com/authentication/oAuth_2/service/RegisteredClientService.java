package com.authentication.oAuth_2.service;

import com.authentication.oAuth_2.helper.ClientRegisterData;
import com.authentication.oAuth_2.helper.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class RegisteredClientService {
    private RegisteredClientRepository registeredClientRepository;

    @Autowired
    public RegisteredClientService(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    public void clientRegistration(ClientRegisterData clientRegisterData) throws ResponseException {
        System.out.println("SEVICE Before setValue clientRegisterData == " + clientRegisterData.getClientName() + " ; "
                + clientRegisterData.getScopes() + " ; " + clientRegisterData.getRedirectURL());
        Consumer<Set<String>> insertStringsToBuilderSet = (stringSet) -> stringSet
                .addAll(clientRegisterData.getRedirectURL());
        Consumer<Set<String>> scopeSET = (stringSet) -> stringSet
                .addAll(clientRegisterData.getScopes());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String id = passwordEncoder.encode(String.valueOf(Math.random()));
        String clientId = passwordEncoder.encode(String.valueOf(Math.random()));
        String clientSecret = passwordEncoder.encode(String.valueOf(Math.random()));


        RegisteredClient clientRegister = RegisteredClient.withId(id)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(clientRegisterData.getScopes().stream().map((scope)-> scope).toString())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUris(insertStringsToBuilderSet)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .build();
        System.out.println("SERVICE clientRegisterData == " + clientRegisterData.getClientName() + " ; "
                + clientRegisterData.getScopes() + " ; " + clientRegisterData.getRedirectURL());
        System.out.println("Client Exist in DataBase  == " + registeredClientRepository
                .findByClientId(clientRegisterData.getClientName()));

        if(registeredClientRepository.findByClientId(clientRegisterData.getClientName()) != null) throw new ResponseException("Такой клиент уже существует");

        registeredClientRepository.save(clientRegister);


    }
}


//        private String id;
//        private String clientId;
//        private Instant clientIdIssuedAt;
//        private String clientSecret;
//        private Instant clientSecretExpiresAt;
//        private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
//        private Set<AuthorizationGrantType> authorizationGrantTypes;

//        private ClientSettings clientSettings;
//        private TokenSettings tokenSettings;


//------------------------------------------
//    private String clientName;
//    private Set<String> redirectUris;
//    private Set<String> postLogoutRedirectUris;
//    private Set<String> scopes;