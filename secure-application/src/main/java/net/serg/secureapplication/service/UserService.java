package net.serg.secureapplication.service;

import lombok.RequiredArgsConstructor;
import net.serg.secureapplication.dto.UserDto;
import net.serg.secureapplication.entity.User;  // replace with your entity package
import net.serg.secureapplication.repository.UserRepository;  // replace with your repository package
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public List<UserDto> findBlockedUsers() {
        var user = userRepository.findUsersByLockTimeAfter(LocalDateTime
                                                           .now().minusMinutes(5));
        return user.stream().map(this::convertToDTO).toList();
    }

    private UserDto convertToDTO(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setLockTime(user.getLockTime());
        userDTO.setLoginAttempt(user.getLoginAttempt());
        return userDTO;
    }
}