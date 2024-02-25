package net.serg.secureapplication.config;

import net.serg.secureapplication.entity.User;
import net.serg.secureapplication.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {
    @Bean
    public ApplicationRunner initializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            createUserIfNotExists(userRepository, passwordEncoder, "user1", "password", Set.of("VIEW_INFO"));
            createUserIfNotExists(userRepository, passwordEncoder, "user2", "password", Set.of("VIEW_ADMIN"));
            createUserIfNotExists(userRepository, passwordEncoder, "user3", "password", Set.of("VIEW_INFO", "VIEW_ADMIN"));
        };
    }

    private void createUserIfNotExists(UserRepository userRepository, PasswordEncoder passwordEncoder, String username, String password, Set<String> authorities) {
        if (userRepository.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setAuthorities(authorities);
            userRepository.save(user);
        }
    }
}