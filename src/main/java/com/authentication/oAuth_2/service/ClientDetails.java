package com.authentication.oAuth_2.service;

import com.authentication.oAuth_2.helper.entity.LoginData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public class ClientDetails implements UserDetails {
    private RegisteredClient registeredClient;
    private LoginData clientLoginData;


    public ClientDetails(RegisteredClient registeredClient, LoginData clientLoginData) {
        this.registeredClient = registeredClient;
        this.clientLoginData = clientLoginData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(clientLoginData.getRole().getRoleType().name()));
    }


    public Instant getClientExpireAt() {
        return registeredClient.getClientSecretExpiresAt();
    }

    public String getClientId() {
        return registeredClient.getClientId();
    }

    public String getClientSecret() {
        return registeredClient.getClientSecret();
    }

    @Override
    public String getPassword() {
        return clientLoginData.getPassword();
    }

    @Override
    public String getUsername() {
        return clientLoginData.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
