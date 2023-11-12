package com.authentication.oAuth_2.helper.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

// Правильно хранить все регистрационные данные в одной таблице,
// раз они между собой не отличаются
@Entity
public class LoginData {
    @Id
    @GeneratedValue
    private int id;

    @Size(min = 2,max = 99)
    private String userName;
    @Size(min = 2,max = 99)
    private String password;
    @OneToOne
    private Role role;

    public LoginData(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginData() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}