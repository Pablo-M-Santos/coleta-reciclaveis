package com.pablomoreira.recycle_scheduler.RecyclableMaterial.mappers;

import com.pablomoreira.recycle_scheduler.RecyclableMaterial.DTOs.RecyclableMaterialDTO;
import com.pablomoreira.recycle_scheduler.RecyclableMaterial.models.RecyclableMaterial;

public class RecyclableMaterialMapper {
    public static RecyclableMaterialDTO toDTO(RecyclableMaterial material) {
        RecyclableMaterialDTO dto = new RecyclableMaterialDTO();
        dto.setId(material.getId());
        dto.setName(material.getName());
        dto.setDescription(material.getDescription());
        return dto;
    }
}

