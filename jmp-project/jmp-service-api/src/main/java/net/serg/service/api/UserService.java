package net.serg.service.api;

import net.serg.dto.UserRequestDto;
import net.serg.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto user);
    UserResponseDto updateUser(UserRequestDto user);
    void deleteUser(Long id);
    UserResponseDto getUser(Long id);
    List<UserResponseDto> getAllUsers();
}