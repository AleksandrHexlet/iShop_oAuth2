package com.authentication.oAuth_2.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

@Configuration
@EnableWebSecurity

public class SecurityConfig {


    @Bean

    public SecurityFilterChain permitRegistration(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                                .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                                .requestMatchers("/oauth/client/registration/**").permitAll()
                                .requestMatchers("/oauth/client/login/**").permitAll()
//                                .requestMatchers("/**").permitAll()
//                        .anyRequest().authenticated()
                )
//                /client/authorization/success
                .formLogin(form -> form
                        .usernameParameter("clientName").passwordParameter("password")
                        .loginPage("/oauth/client/authorization")
                        .failureUrl("/oauth/client/authorization?failed")
                        .loginProcessingUrl("/oauth/client/authorization") //in form ---> th:action="@{/oauth/client/authorization}"
                        .defaultSuccessUrl("/client/authorization/success").permitAll())
                .logout(logout -> logout.logoutUrl("/oauth/client/logout")
                        .logoutSuccessUrl("/oauth/client/authorization").permitAll());
        // ???? is it correct ?
        // what else needs to be added ????



//        http.formLogin(Customizer.withDefaults());

        return http.build();
    }

}

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorize) -> authorize
//                                .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
//                                .requestMatchers("/endpoint", "/endpoint2").permitAll()
//                                .requestMatchers("/adminHTML/**").hasAnyRole("READONLY_ADMIN", "ADMIN")
////                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .usernameParameter("username").passwordParameter("password")
//                        .loginPage("/adminHTML/login").failureUrl("/adminHTML/login?failed")
//                        .loginProcessingUrl("/adminHTML/login").defaultSuccessUrl("/adminHTML/account").permitAll())
//                .logout(logout -> logout.logoutUrl("/adminHTML/logout")
//                        .logoutSuccessUrl("/adminHTML/login").permitAll())
//                .authorizeHttpRequests((authorize) -> authorize
//                        .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
//                        .requestMatchers("/api/customer/registration", "/api/customer/authorization").permitAll()
//                        .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }