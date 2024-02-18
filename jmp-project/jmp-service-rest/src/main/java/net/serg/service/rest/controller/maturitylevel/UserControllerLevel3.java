package net.serg.service.rest.controller.maturitylevel;

import lombok.RequiredArgsConstructor;
import net.serg.dto.UserRequestDto;
import net.serg.dto.UserResponseDto;
import net.serg.service.api.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/level3/users")
@RequiredArgsConstructor
public class UserControllerLevel3 {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<EntityModel<UserResponseDto>> createUser(@RequestBody UserRequestDto userRequestDto) {
        var userResponseDto = userService.createUser(userRequestDto);

        Link selfLink = WebMvcLinkBuilder
            .linkTo(methodOn(UserControllerLevel3.class).getUser(userResponseDto.getId()))
            .withSelfRel();
        Link deleteLink = WebMvcLinkBuilder
            .linkTo(methodOn(UserControllerLevel3.class).deleteUser(userResponseDto.getId()))
            .withRel("delete");
        Link updateLink = WebMvcLinkBuilder
            .linkTo(methodOn(UserControllerLevel3.class).updateUser(userRequestDto))
            .withRel("update");

        var uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userResponseDto.getId())
            .toUri();

        return ResponseEntity
            .created(uri)
            .body(EntityModel.of(userResponseDto, selfLink, deleteLink, updateLink));
    }

    @PutMapping
    public ResponseEntity<EntityModel<UserResponseDto>> updateUser(@RequestBody UserRequestDto userRequestDto) {
        if (userRequestDto.getId() == null) {
            throw new IllegalArgumentException("Id cannot be null when updating user.");
        }
        var userResponseDto = userService.updateUser(userRequestDto);

        Link selfLink = WebMvcLinkBuilder
            .linkTo(methodOn(UserControllerLevel3.class).getUser(userResponseDto.getId()))
            .withSelfRel();
        Link deleteLink = WebMvcLinkBuilder
            .linkTo(methodOn(UserControllerLevel3.class).deleteUser(userResponseDto.getId()))
            .withRel("delete");

        return ResponseEntity.ok(EntityModel.of(userResponseDto, selfLink, deleteLink));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDto>> getUser(@PathVariable Long id) {
        var userResponseDto = userService.getUser(id);

        Link deleteLink = WebMvcLinkBuilder
            .linkTo(methodOn(UserControllerLevel3.class).deleteUser(id))
            .withRel("delete");
        Link updateLink = WebMvcLinkBuilder
            .linkTo(methodOn(UserControllerLevel3.class).updateUser(new UserRequestDto()))
            .withRel("update");

        return ResponseEntity.ok(EntityModel.of(userResponseDto, deleteLink, updateLink));
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<UserResponseDto>>> getAllUser() {
        var userResponseDtos = userService.getAllUsers();
        var users = userResponseDtos
            .stream()
            .map(user -> EntityModel.of(
                user,
                WebMvcLinkBuilder
                    .linkTo(methodOn(UserControllerLevel3.class).getUser(user.getId()))
                    .withSelfRel(),
                WebMvcLinkBuilder
                    .linkTo(methodOn(UserControllerLevel3.class).deleteUser(user.getId()))
                    .withRel("delete"),
                WebMvcLinkBuilder
                    .linkTo(methodOn(UserControllerLevel3.class).updateUser(new UserRequestDto()))
                    .withRel("update")))
            .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }
}