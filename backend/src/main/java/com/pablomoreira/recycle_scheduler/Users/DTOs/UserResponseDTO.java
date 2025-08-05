package com.pablomoreira.recycle_scheduler.Users.DTOs;

import com.pablomoreira.recycle_scheduler.Users.models.UserRoleEnum;
import lombok.Builder;

@Builder
public record UserResponseDTO(
        int id,
        String name,
        String email,
        UserRoleEnum role
){
}
