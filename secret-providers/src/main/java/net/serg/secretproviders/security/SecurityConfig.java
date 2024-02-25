package net.serg.secretproviders.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests((authorize) -> authorize
                    .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());

            return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            UserDetails sender = User
                .withDefaultPasswordEncoder()
                .username("sender")
                .password("password")
                .roles("STANDARD")
                .build();

            UserDetails recipient = User
                .withDefaultPasswordEncoder()
                .username("recipient")
                .password("password")
                .roles("STANDARD")
                .build();

            return new InMemoryUserDetailsManager(sender, recipient);
        }
    
}
