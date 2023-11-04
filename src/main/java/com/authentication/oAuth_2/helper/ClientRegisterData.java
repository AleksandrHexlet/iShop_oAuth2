package com.authentication.oAuth_2.helper;

import jakarta.persistence.Entity;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Set;


public class ClientRegisterData {

    private String clientName;
    private Set<String> redirectURL;
    private String postLogoutRedirectURL;
    private Set<String> scopes;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Set<String> getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(Set<String> redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getPostLogoutRedirectURL() {
        return postLogoutRedirectURL;
    }

    public void setPostLogoutRedirectURL(String postLogoutRedirectURL) {
        this.postLogoutRedirectURL = postLogoutRedirectURL;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }
}
