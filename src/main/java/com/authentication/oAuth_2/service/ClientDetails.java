package com.authentication.oAuth_2.service;

import com.authentication.oAuth_2.helper.entity.ClientLoginData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.time.Instant;
import java.util.Collection;

public class ClientDetails implements UserDetails {
    private RegisteredClient registeredClient;
    ClientLoginData clientLoginData;


    public ClientDetails(RegisteredClient registeredClient, ClientLoginData clientLoginData) {
        this.registeredClient = registeredClient;
        this.clientLoginData = clientLoginData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return clientLoginData.getClientName();
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
