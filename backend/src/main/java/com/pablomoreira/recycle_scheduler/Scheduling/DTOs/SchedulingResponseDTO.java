package com.pablomoreira.recycle_scheduler.Scheduling.DTOs;

import com.pablomoreira.recycle_scheduler.Scheduling.models.Scheduling;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SchedulingResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String telephone;
    private String email;
    private List<String> material;
    private LocalDate datePreferred;
    private String status;
    private String codeProtocol;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SchedulingResponseDTO(Scheduling s) {
        this.id = s.getId();
        this.name = s.getName();
        this.address = s.getAddress();
        this.telephone = s.getTelephone();
        this.email = s.getEmail();
        this.material = s.getMaterial();
        this.datePreferred = s.getDatePreferred();
        this.status = s.getStatus().name();
        this.codeProtocol = s.getCodeProtocol();
        this.createdAt = s.getCreatedAt();
        this.updatedAt = s.getUpdatedAt();
    }
}
