package net.serg.secureapplication.repository;

import net.serg.secureapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findUsersByLockTimeAfter(LocalDateTime localDateTime);
}