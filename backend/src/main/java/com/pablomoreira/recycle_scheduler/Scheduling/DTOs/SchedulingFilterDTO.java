package com.pablomoreira.recycle_scheduler.Scheduling.DTOs;

import com.pablomoreira.recycle_scheduler.Scheduling.models.SchedulingStatus;

import java.time.LocalDate;

public class SchedulingFilterDTO {
    private LocalDate datePreferred;
    private SchedulingStatus status;
    private String material;
}

