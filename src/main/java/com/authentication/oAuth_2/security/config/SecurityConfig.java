package com.authentication.oAuth_2.security.config;

import com.authentication.oAuth_2.service.ClientDetailsService;
import com.authentication.oAuth_2.service.TraderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final ClientDetailsService clientDetailsService;
    private final TraderDetailsService traderDetailsService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, ClientDetailsService clientDetailsService, TraderDetailsService traderDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.clientDetailsService = clientDetailsService;
        this.traderDetailsService = traderDetailsService;
    }

    // Провайдер авторизации владельцев клиентских приложений
    private AuthenticationProvider clientAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // будет использовать clientDetailsService для получения данных из БД
        provider.setUserDetailsService(clientDetailsService);
        // будет использовать passwordEncoder для сравнения паролей
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    private AuthenticationProvider traderAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // будет использовать traderDetailsService для получения данных из БД
        provider.setUserDetailsService(traderDetailsService);
        // будет использовать passwordEncoder для сравнения паролей
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    @Bean
    @Order(1)
    public SecurityFilterChain permitClients(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/oauth/client/**")
                // установили ответственного за авторизацию владельцев клиентских приложений
                .authenticationProvider(clientAuthenticationProvider())
                .authorizeHttpRequests((authorize) -> authorize
                        .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                        .requestMatchers("/oauth/client/registration", "/oauth/client/authorization").permitAll()
                        // сделаем правильно, с ролями
                        .requestMatchers("/oauth/client/authorization/success").hasRole("CLIENT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .usernameParameter("clientName").passwordParameter("password")
                        .loginPage("/oauth/client/authorization") // getMapping
                        .failureUrl("/oauth/client/authorization?failed")
                        .loginProcessingUrl("/oauth/client/authorization") // // postMapping //in form ---> th:action="@{/oauth/client/authorization}"
                        // не было oauth в defaultSuccessUrl
                        .defaultSuccessUrl("/oauth/client/authorization/success").permitAll())
                .logout(logout -> logout.logoutUrl("/oauth/client/logout")
                        .logoutSuccessUrl("/oauth/client/authorization").permitAll());
//        http.formLogin(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public SecurityFilterChain permitTrader(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(traderAuthenticationProvider())
                .authorizeHttpRequests((authorize) -> authorize
                        .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                        .requestMatchers("/oauth/trader/authorization").permitAll()
                        // разрешаем всем запросы к static, css и тп папкам
                        .requestMatchers("/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .usernameParameter("username").passwordParameter("password")
                        .loginPage("/oauth/trader/authorization") // getMapping
                        .failureUrl("/oauth/trader/authorization?failed")
                        .defaultSuccessUrl("/oauth/trader/authorization/success")
                        .loginProcessingUrl("/oauth/trader/authorization")); // postMapping //in form ---> th:action="@{/oauth/trader/authorization}"
//        http.formLogin(Customizer.withDefaults());
        return http.build();
    }
}

//http://localhost:9090/oauth2/authorize?client_id=$2a$10$XPItnalALTnwWei0WTnlAulpmP2RatO0REzG9m/QjgwgdZFstfmv.&response_type=code&redirect_uri=http://app.ru&scope=openid%20read