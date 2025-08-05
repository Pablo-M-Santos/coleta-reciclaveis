package com.pablomoreira.recycle_scheduler.Scheduling.DTOs;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SchedulingRequestDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Telephone is mandatory")
    @Pattern(regexp = "\\d{8,15}", message = "Telephone number must contain 8 to 15 numeric digits")
    private String telephone;

    @Email(message = "Invalid email")
    private String email;

    @NotEmpty(message = "Enter at least one material")
    private List<@NotBlank(message = "Material cannot be empty") String> material;

    @NotNull(message = "Preferred date is mandatory")
    private LocalDate datePreferred;
}
