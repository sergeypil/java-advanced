package net.serg.secureapplication.controller;

import lombok.RequiredArgsConstructor;
import net.serg.secureapplication.dto.UserDto;
import net.serg.secureapplication.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/blocked")
    public List<UserDto> getBlockedUsers() {
        return userService.findBlockedUsers();
    }
}