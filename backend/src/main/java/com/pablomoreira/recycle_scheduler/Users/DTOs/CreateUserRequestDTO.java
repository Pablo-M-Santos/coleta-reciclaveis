package com.pablomoreira.recycle_scheduler.Users.DTOs;

import com.pablomoreira.recycle_scheduler.Users.models.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDTO(

        @NotBlank(message = "The name cannot be empty or contain only spaces.")
        String name,

        @Email(message = "Email must be valid.")
        @NotBlank(message = "Email cannot be empty or contain only spaces.")
        String email,

        @NotBlank(message = "Password cannot be empty or contain only spaces.")
        String password,

        @NotNull(message = "Access level cannot be null.")
        UserRoleEnum role
) {
}
