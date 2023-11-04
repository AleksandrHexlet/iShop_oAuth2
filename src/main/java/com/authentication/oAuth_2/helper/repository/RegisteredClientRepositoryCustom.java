package com.authentication.oAuth_2.helper.repository;


import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;


public class RegisteredClientRepositoryCustom extends JdbcRegisteredClientRepository {


    /**
     * Constructs a {@code JdbcRegisteredClientRepository} using the provided parameters.
     *
     * @param jdbcOperations the JDBC operations
     */
    public RegisteredClientRepositoryCustom(JdbcOperations jdbcOperations) {
        super(jdbcOperations);
    }


    public boolean isExist(String userName) {
        Integer countClient = getJdbcOperations()
                .queryForObject("select count(*) from client where clientName= ?", Integer.class, userName);
        return countClient != null;
    }

    ;
}
