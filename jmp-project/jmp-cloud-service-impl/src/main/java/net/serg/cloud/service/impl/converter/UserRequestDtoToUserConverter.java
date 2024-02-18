package net.serg.cloud.service.impl.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import net.serg.cloud.service.impl.entity.User;
import net.serg.dto.UserRequestDto;

import java.time.LocalDate;

@Component
public class UserRequestDtoToUserConverter implements Converter<UserRequestDto, User> {

    @Override
    public User convert(UserRequestDto source) {
        return User
            .builder()
            .id(source.getId())
            .name(source.getName())
            .surname(source.getSurname())
            .birthday(LocalDate.parse(source.getBirthday()))
            .build();
    }
}