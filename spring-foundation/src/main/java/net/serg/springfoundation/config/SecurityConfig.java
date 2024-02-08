package net.serg.springfoundation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain permitAllSecurityFilterChain(HttpSecurity http) throws Exception {

                http.authorizeHttpRequests(authz -> authz
                    .requestMatchers("/actuator/**").permitAll()
                    .anyRequest().authenticated());

        return http.build();
    }
}