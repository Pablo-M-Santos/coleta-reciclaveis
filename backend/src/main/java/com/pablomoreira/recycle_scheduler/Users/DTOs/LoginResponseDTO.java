package com.pablomoreira.recycle_scheduler.Users.DTOs;


import com.pablomoreira.recycle_scheduler.Users.models.UserRoleEnum;

public record LoginResponseDTO(String token,String name, String email, UserRoleEnum role) {

}
