package com.authentication.oAuth_2.service;

import com.authentication.oAuth_2.helper.ClientRegisterData;
import com.authentication.oAuth_2.helper.ResponseException;
import com.authentication.oAuth_2.helper.entity.ClientLoginData;
import com.authentication.oAuth_2.helper.repository.ClientLoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class RegisteredClientService {
    private RegisteredClientRepository registeredClientRepository;
    private ClientLoginDataRepository clientLoginDataRepository;

    @Autowired
    public RegisteredClientService(RegisteredClientRepository registeredClientRepository,
                                   ClientLoginDataRepository clientLoginDataRepository) {
        this.registeredClientRepository = registeredClientRepository;
        this.clientLoginDataRepository = clientLoginDataRepository;
    }

    public RegisteredClient clientRegistration(ClientRegisterData clientRegisterData) throws ResponseException {

//        Consumer<Set<String>> insertStringsToBuilderSet = (stringSet) -> stringSet
//                .addAll(clientRegisterData.getRedirectURL());
        if (registeredClientRepository.findById(clientRegisterData.getClientName().strip()) != null)
            throw new ResponseException("Client exist.Такой клиент уже существует");
        if (clientLoginDataRepository.findByClientName(clientRegisterData.getClientName().strip()) != null)
            throw new ResponseException("Login exist .Такой логин уже существует");

        Consumer<Set<String>> scopeSET = (stringSet) -> {
            stringSet.addAll(clientRegisterData.getScopes());
            stringSet.add(OidcScopes.OPENID);
        };

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String id = passwordEncoder.encode(String.valueOf(Math.random()));
        String clientId = passwordEncoder.encode(String.valueOf(Math.random()));
        String clientSecret = passwordEncoder.encode(String.valueOf(Math.random()));
        Instant clientIdIssuedAt = ZonedDateTime.now(ZoneId.systemDefault()).toInstant();
        Instant clientSecretExpiresAt = ZonedDateTime.now(ZoneId.systemDefault()).plusMonths(6).toInstant();

        RegisteredClient clientRegister = RegisteredClient.withId(clientRegisterData.getClientName().strip())
                .clientId(clientId)
//                .tokenSettings(clientRegisterData.getPassword().strip())
                .clientSecret(clientSecret)
                .clientIdIssuedAt(clientIdIssuedAt)
                .clientSecretExpiresAt(clientSecretExpiresAt)
//                .scope(clientRegisterData.getScopes().stream().map((scope)-> scope).toString())
                .scopes(scopeSET)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUris(insertStringsToBuilderSet)
                .redirectUri(clientRegisterData.getRedirectURL().strip())
                .postLogoutRedirectUri(clientRegisterData.getPostLogoutRedirectURL().strip())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .build();
        System.out.println("SERVICE clientRegisterData == " + clientRegisterData.getClientName() + " ; "
                + clientRegisterData.getScopes() + " ; " + clientRegisterData.getRedirectURL());
        System.out.println("Client Exist in DataBase  == " + registeredClientRepository
                .findById(clientRegisterData.getClientName()));

        String clientPassword = passwordEncoder.encode(String.valueOf(clientRegisterData.getPassword()));
        ClientLoginData clientLoginData = new ClientLoginData(clientRegisterData.getClientName(), clientPassword);


        registeredClientRepository.save(clientRegister);

        clientLoginDataRepository.save(clientLoginData);
        return clientRegister;
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