package com.authentication.oAuth_2.service;


import com.authentication.oAuth_2.helper.entity.LoginData;
import com.authentication.oAuth_2.helper.repository.LoginDataRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TraderDetailsService implements UserDetailsService {
    private LoginDataRepository loginDataRepository;

    public TraderDetailsService(LoginDataRepository loginDataRepository) {
        this.loginDataRepository = loginDataRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginData loginData = loginDataRepository.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Пользователь с именем %s не зарегистрирован", username)));
        GrantedAuthority authority = new SimpleGrantedAuthority(loginData.getRole().getRoleType().name());
        return new User(username, loginData.getPassword(), Set.of(authority));
    }
}
