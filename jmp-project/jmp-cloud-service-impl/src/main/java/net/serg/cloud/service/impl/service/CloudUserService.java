package net.serg.cloud.service.impl.service;

import lombok.RequiredArgsConstructor;
import net.serg.cloud.service.impl.converter.UserRequestDtoToUserConverter;
import net.serg.cloud.service.impl.converter.UserToUserResponseDtoConverter;
import net.serg.dto.UserRequestDto;
import net.serg.dto.UserResponseDto;
import net.serg.service.api.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CloudUserService implements UserService {

    private final UserDataService userDataService;
    private final UserRequestDtoToUserConverter userRequestDtoToUserConverter;
    private final UserToUserResponseDtoConverter userToUserResponseDtoConverter;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        var user = userRequestDtoToUserConverter.convert(userRequestDto);
        var savedUser = userDataService.save(user);
        return userToUserResponseDtoConverter.convert(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UserRequestDto userRequestDto) {
        var userToUpdate = userDataService
            .getUser(userRequestDto.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        userToUpdate.setName(userRequestDto.getName());
        userToUpdate.setSurname(userRequestDto.getSurname());
        var updatedUser = userDataService.save(userToUpdate);
        return userToUserResponseDtoConverter.convert(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDataService.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        var user = userDataService
            .getUser(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return userToUserResponseDtoConverter.convert(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userDataService
            .getAll()
            .stream()
            .map(userToUserResponseDtoConverter::convert)
            .toList();
    }
}