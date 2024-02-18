package net.serg.cloud.service.impl.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import net.serg.dto.UserResponseDto;
import net.serg.cloud.service.impl.entity.User;

@Component
public class UserToUserResponseDtoConverter implements Converter<User, UserResponseDto> {

    @Override
    public UserResponseDto convert(User source) {
        return UserResponseDto
            .builder()
            .id(source.getId())
            .name(source.getName())
            .surname(source.getSurname())
            .birthday(source
                          .getBirthday()
                          .toString())
            .build();
    }
}