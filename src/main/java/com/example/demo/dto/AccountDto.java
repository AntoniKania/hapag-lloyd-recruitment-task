package com.example.demo.dto;

import com.example.demo.model.Gender;

import java.time.LocalDateTime;

public record AccountDto(
        String username,
        Gender gender,
        Integer age,
        LocalDateTime creationDate
) {
}
