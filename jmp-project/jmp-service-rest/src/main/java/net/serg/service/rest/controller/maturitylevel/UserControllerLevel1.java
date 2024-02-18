package net.serg.service.rest.controller.maturitylevel;

import lombok.RequiredArgsConstructor;
import net.serg.dto.UserRequestDto;
import net.serg.dto.UserResponseDto;
import net.serg.service.api.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/level1/users")
@RequiredArgsConstructor
public class UserControllerLevel1 {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        var userResponseDto = userService.createUser(userRequestDto);
        var uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userResponseDto.getId())
            .toUri();
        return ResponseEntity
            .created(uri)
            .body(userResponseDto);
    }

    @PostMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto userRequestDto) {
        if (userRequestDto.getId() == null) {
            throw new IllegalArgumentException("Id cannot be null when updating user.");
        }
        var userResponseDto = userService.updateUser(userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @PostMapping("/{id}/get")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        var userResponseDto = userService.getUser(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/getall")
    public ResponseEntity<List<UserResponseDto>> getAllUser() {
        var userResponseDtos = userService.getAllUsers();
        return ResponseEntity.ok(userResponseDtos);
    }
}