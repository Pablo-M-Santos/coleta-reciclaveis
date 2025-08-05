package com.pablomoreira.recycle_scheduler.Scheduling.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_appointments")
public class Scheduling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String telephone;
    private String email;

    @ElementCollection
    private List<String> material;

    private LocalDate datePreferred;

    @Enumerated(EnumType.STRING)
    private SchedulingStatus status = SchedulingStatus.PENDING;

    private String codeProtocol;

    private String justification;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

}
