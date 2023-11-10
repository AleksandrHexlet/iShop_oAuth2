package com.authentication.oAuth_2.service;

import com.authentication.oAuth_2.helper.ClientRegisterData;
import com.authentication.oAuth_2.helper.ResponseException;
import com.authentication.oAuth_2.helper.entity.LoginData;
import com.authentication.oAuth_2.helper.entity.Role;
import com.authentication.oAuth_2.helper.repository.LoginDataRepository;
import com.authentication.oAuth_2.helper.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Set;
import java.util.function.Consumer;

@Service
public class RegisteredClientService {
    private final RoleRepository roleRepository;
    private final LoginDataRepository loginDataRepository;
    private RegisteredClientRepository registeredClientRepository;
    private  PasswordEncoder passwordEncoder;

    @Autowired
    public RegisteredClientService(RegisteredClientRepository registeredClientRepository,
                                   PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                                   LoginDataRepository loginDataRepository) {
        this.registeredClientRepository = registeredClientRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.loginDataRepository = loginDataRepository;
    }

    public RegisteredClient clientRegistration(ClientRegisterData clientRegisterData) throws ResponseException {

//        Consumer<Set<String>> insertStringsToBuilderSet = (stringSet) -> stringSet
//                .addAll(clientRegisterData.getRedirectURL());
        if (registeredClientRepository.findById(clientRegisterData.getClientName().strip()) != null)
            throw new ResponseException("Client exist.Такой клиент уже существует");
        if (loginDataRepository.findByUserName(clientRegisterData.getClientName().strip()).isPresent())
            throw new ResponseException("Login exist .Такой логин уже существует");

        Consumer<Set<String>> scopeSET = (stringSet) -> {
            stringSet.addAll(clientRegisterData.getScopes());
            stringSet.add(OidcScopes.OPENID);
        };


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

        String clientPassword = passwordEncoder.encode(clientRegisterData.getPassword());
        LoginData clientLoginData = new LoginData(clientRegisterData.getClientName(), clientPassword);
        System.out.println("clientLoginData == " + clientLoginData.getUserName() + " ; " + clientLoginData.getPassword());

        registeredClientRepository.save(clientRegister);

        // назначаем роль владельцам клиентских приложений
        Role clientRole = roleRepository.findByRoleType(Role.RoleType.ROLE_CLIENT).orElseGet(()->{
            Role role = new Role();
            role.setRoleType(Role.RoleType.ROLE_CLIENT);
            return roleRepository.save(role);
        });

        clientLoginData.setRole(clientRole);
        loginDataRepository.save(clientLoginData);
        return clientRegister;
    }
}
