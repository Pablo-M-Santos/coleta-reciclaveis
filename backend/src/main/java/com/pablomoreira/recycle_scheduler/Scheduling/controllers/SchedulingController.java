package com.pablomoreira.recycle_scheduler.Scheduling.controllers;

import com.pablomoreira.recycle_scheduler.Scheduling.DTOs.SchedulingDetailDTO;
import com.pablomoreira.recycle_scheduler.Scheduling.DTOs.SchedulingRequestDTO;
import com.pablomoreira.recycle_scheduler.Scheduling.DTOs.SchedulingResponseDTO;
import com.pablomoreira.recycle_scheduler.Scheduling.DTOs.SchedulingStatusUpdateDTO;
import com.pablomoreira.recycle_scheduler.Scheduling.models.Scheduling;
import com.pablomoreira.recycle_scheduler.Scheduling.models.SchedulingStatus;
import com.pablomoreira.recycle_scheduler.Scheduling.services.SchedulingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")


public class SchedulingController {

    @Autowired
    private SchedulingService schedulingService;

    @PostMapping
    public ResponseEntity<?> scheduleCollection(@RequestBody @Valid SchedulingRequestDTO request) {
        Scheduling scheduling = schedulingService.createScheduling(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Appointment created successfully! Protocol: " + scheduling.getCodeProtocol());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<SchedulingResponseDTO>> listAppointments() {
        List<Scheduling> appointments = schedulingService.listAll();
        List<SchedulingResponseDTO> response = appointments.stream()
                .map(SchedulingResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<SchedulingDetailDTO> getDetail(@PathVariable Long id) {
        var scheduling = schedulingService.findById(id);
        if (scheduling.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SchedulingDetailDTO dto = new SchedulingDetailDTO(scheduling.get());
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody @Valid SchedulingStatusUpdateDTO dto) {
        SchedulingDetailDTO updated = schedulingService.updateStatus(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<List<SchedulingDetailDTO>> filterAppointments(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePreferred,
            @RequestParam(required = false) SchedulingStatus status,
            @RequestParam(required = false) String material
    ) {
        List<SchedulingDetailDTO> appointments = schedulingService.filter(datePreferred, status, material);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/protocol/{codeProtocol}")
    public ResponseEntity<SchedulingDetailDTO> getByProtocol(@PathVariable String codeProtocol) {
        var scheduling = schedulingService.findByCodeProtocol(codeProtocol);
        if (scheduling.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SchedulingDetailDTO dto = new SchedulingDetailDTO(scheduling.get());
        return ResponseEntity.ok(dto);
    }




}
