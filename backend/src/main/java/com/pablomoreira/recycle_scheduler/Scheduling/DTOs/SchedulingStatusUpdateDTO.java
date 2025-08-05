package com.pablomoreira.recycle_scheduler.Scheduling.DTOs;

import com.pablomoreira.recycle_scheduler.Scheduling.models.SchedulingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SchedulingStatusUpdateDTO {
    @NotNull
    private SchedulingStatus status;

    private String justification;

}
