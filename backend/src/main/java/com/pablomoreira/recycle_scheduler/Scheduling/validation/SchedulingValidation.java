package com.pablomoreira.recycle_scheduler.Scheduling.validation;

import com.pablomoreira.recycle_scheduler.RecyclableMaterial.services.RecyclableMaterialService;
import com.pablomoreira.recycle_scheduler.Scheduling.DTOs.SchedulingRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SchedulingValidation {

    @Autowired
    private RecyclableMaterialService recyclableMaterialService;

    public void validateSchedulingRequest(SchedulingRequestDTO request) {
        validateDate(request.getDatePreferred());
        validateMaterials(request.getMaterial());
    }

    private void validateDate(LocalDate date) {
        LocalDate minDate = LocalDate.now().plusDays(2);
        if (date.isBefore(minDate)) {
            throw new IllegalArgumentException("Date must be at least 2 days after today.");
        }
    }

    private void validateMaterials(List<String> materials) {
        List<String> validMaterials = recyclableMaterialService.findAll()
                .stream()
                .map(material -> material.getName())
                .toList();

        for (String material : materials) {
            if (!validMaterials.contains(material)) {
                throw new IllegalArgumentException("Invalid material: " + material);
            }
        }
    }
}
