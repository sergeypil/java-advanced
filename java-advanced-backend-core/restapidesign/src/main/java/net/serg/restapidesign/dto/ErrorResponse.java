package net.serg.restapidesign.dto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String message;
}