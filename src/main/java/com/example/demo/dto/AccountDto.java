package com.example.demo.dto;

import com.example.demo.model.Gender;
import jakarta.validation.constraints.NotNull;

public record AccountDto(
        @NotNull(message = "Username can not be null") String username,
        @NotNull(message = "Gender can not be null") Gender gender,
        @NotNull(message = "Age can not be null") Integer age
) {
}
