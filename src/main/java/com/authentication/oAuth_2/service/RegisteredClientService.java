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
//        Consumer<Set<String>> redirectUrisConsumer = (setSTR) -> setSTR.stream().map((item) -> item);
//        Consumer<Set<String>> scopeSET = (setScope) -> setScope.stream().map((item) -> item);
//            .redirectUri(redirectUrisConsumer);
        Consumer<Set<String>> insertStringsToBuilderSet = (stringSet) -> stringSet
                .addAll(clientRegisterData.getRedirectUris());
        Consumer<Set<String>> scopeSET = (stringSet) -> stringSet
                .addAll(clientRegisterData.getScopes());
//        на 49 строке я заполнил scope  через stream. Через consumer не получилось так как
//  метод  .scope(clientRegisterData.getScopes().stream().map((scope)-> scope).toString()) просит строку,
//  а не set<String>


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

        if(registeredClientRepository.findByClientId(clientId).getId().equals(null)) throw new ResponseException("Такой клиент уже существует");
        // тут надо проверять по userName, по id проверять нет смысла т.к. мы его тут же и создаём
        // на 44 строке и его ещё нет в базе данных. Я не нашел метода проверки по имени
        // в registeredClientRepository, но попробовал создать свой репо и extend от него.
        // Это надо обсудить :):):)

        registeredClientRepository.save(clientRegister);
//        registeredClientRepository.findByClientID

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