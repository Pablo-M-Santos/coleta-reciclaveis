package com.pablomoreira.recycle_scheduler.Scheduling.mappers;

import com.pablomoreira.recycle_scheduler.Scheduling.DTOs.SchedulingDetailDTO;
import com.pablomoreira.recycle_scheduler.Scheduling.models.Scheduling;

public class SchedulingMapper {
    public static SchedulingDetailDTO toDetailDTO(Scheduling scheduling) {
        SchedulingDetailDTO dto = new SchedulingDetailDTO(scheduling);
        dto.setId(scheduling.getId());
        dto.setName(scheduling.getName());
        dto.setAddress(scheduling.getAddress());
        dto.setMaterial(scheduling.getMaterial());
        dto.setDatePreferred(scheduling.getDatePreferred());
        dto.setTelephone(scheduling.getTelephone());
        dto.setEmail(scheduling.getEmail());
        dto.setStatus(scheduling.getStatus().name());
        dto.setUpdatedAt(scheduling.getUpdatedAt());
        dto.setJustification(scheduling.getJustification());
        return dto;
    }
}

