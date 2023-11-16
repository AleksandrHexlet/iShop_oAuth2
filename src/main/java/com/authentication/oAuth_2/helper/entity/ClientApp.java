package com.authentication.oAuth_2.helper.entity;


import jakarta.persistence.Entity;

@Entity

public class ClientApp extends LoginData {

    private String clientSecretClean;

    public String getClientSecretClean() {
        return clientSecretClean;
    }

    public ClientApp(String userName, String password, String clientSecret) {
        super(userName, password);
        this.clientSecretClean = clientSecret;
    }

    public ClientApp() {

    }

    public void setClientSecretClean(String clientSecret) {
        this.clientSecretClean = clientSecret;
    }
}
