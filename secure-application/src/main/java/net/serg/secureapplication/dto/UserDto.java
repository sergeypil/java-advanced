package net.serg.secureapplication.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private long id;
    private String username;
    private LocalDateTime lockTime;
    private int loginAttempt;
}