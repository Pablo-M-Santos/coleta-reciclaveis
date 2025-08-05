package com.pablomoreira.recycle_scheduler.RecyclableMaterial.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecyclableMaterialCreateDTO {
    @NotBlank
    private String name;
    private String description;
}
