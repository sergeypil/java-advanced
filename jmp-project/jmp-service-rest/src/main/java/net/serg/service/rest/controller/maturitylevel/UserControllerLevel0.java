package net.serg.service.rest.controller.maturitylevel;

import lombok.RequiredArgsConstructor;
import net.serg.dto.UserRequestDto;
import net.serg.service.api.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/level0/users")
@RequiredArgsConstructor
public class UserControllerLevel0 {

    private final UserService userService;
    
    @PostMapping("/{action}")
    public ResponseEntity<?> userAction(
        @RequestBody UserRequestDto userRequestDto,
        @PathVariable("action") String action) {
        switch (action.toLowerCase()) {
            case "create":
                var userResponseDto = userService.createUser(userRequestDto);
                var location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(userResponseDto.getId())
                    .toUri();
                return ResponseEntity
                    .created(location)
                    .body(userResponseDto);
            case "update":
                var updatedUserResponseDto = userService.updateUser(userRequestDto);
                return ResponseEntity.ok(updatedUserResponseDto);
            case "delete":
                userService.deleteUser(userRequestDto.getId());
                return ResponseEntity
                    .noContent()
                    .build();
            case "get":
                var getUserResponseDto = userService.getUser(userRequestDto.getId());
                return ResponseEntity.ok(getUserResponseDto);
            case "getall":
                var userResponseDtos = userService.getAllUsers();
                return ResponseEntity.ok(userResponseDtos);
            default:
                throw new IllegalArgumentException("Invalid action command");
        }
    }
}