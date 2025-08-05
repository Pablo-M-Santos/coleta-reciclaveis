package com.pablomoreira.recycle_scheduler.Scheduling.services;

import com.pablomoreira.recycle_scheduler.RecyclableMaterial.services.RecyclableMaterialService;
import com.pablomoreira.recycle_scheduler.Scheduling.DTOs.SchedulingDetailDTO;
import com.pablomoreira.recycle_scheduler.Scheduling.DTOs.SchedulingRequestDTO;
import com.pablomoreira.recycle_scheduler.Scheduling.DTOs.SchedulingStatusUpdateDTO;
import com.pablomoreira.recycle_scheduler.Scheduling.mappers.SchedulingMapper;
import com.pablomoreira.recycle_scheduler.Scheduling.models.Scheduling;
import com.pablomoreira.recycle_scheduler.Scheduling.models.SchedulingStatus;
import com.pablomoreira.recycle_scheduler.Scheduling.repositories.SchedulingRepository;
import com.pablomoreira.recycle_scheduler.Scheduling.validation.SchedulingValidation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private RecyclableMaterialService recyclableMaterialService;

    @Autowired
    private SchedulingValidation schedulingValidation;


    public Scheduling createScheduling(SchedulingRequestDTO request) {
        schedulingValidation.validateSchedulingRequest(request);

        Scheduling scheduling = new Scheduling();
        scheduling.setName(request.getName());
        scheduling.setAddress(request.getAddress());
        scheduling.setTelephone(request.getTelephone());
        scheduling.setEmail(request.getEmail());
        scheduling.setMaterial(request.getMaterial());
        scheduling.setDatePreferred(request.getDatePreferred());
        scheduling.setCodeProtocol(UUID.randomUUID().toString());

        return schedulingRepository.save(scheduling);
    }

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

    public SchedulingDetailDTO updateStatus(Long id, SchedulingStatusUpdateDTO dto) {
        Scheduling scheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found."));


        if ((dto.getStatus() == SchedulingStatus.COMPLETED || dto.getStatus() == SchedulingStatus.CANCELED  )
                && (dto.getJustification() == null || dto.getJustification().isBlank())) {
            throw new IllegalArgumentException("Justification is mandatory for Completed or Cancelled status.");
        }

        scheduling.setStatus(dto.getStatus());
        scheduling.setJustification(dto.getJustification());
        scheduling.setUpdatedAt(LocalDateTime.now());

        schedulingRepository.save(scheduling);
        return SchedulingMapper.toDetailDTO(scheduling);
    }

    public Optional<Scheduling> findByCodeProtocol(String codeProtocol) {
        return schedulingRepository.findByCodeProtocol(codeProtocol);
    }


    public List<SchedulingDetailDTO> filter(LocalDate datePreferred, SchedulingStatus status, String material) {
        List<Scheduling> all = schedulingRepository.findAll();

        return all.stream()
                .filter(s -> datePreferred == null || s.getDatePreferred().equals(datePreferred))
                .filter(s -> status == null || s.getStatus() == status)
                .filter(s -> material == null ||
                        s.getMaterial().stream()
                                .anyMatch(m -> m.equalsIgnoreCase(material)))
                .map(SchedulingMapper::toDetailDTO)
                .toList();
    }



    public List<Scheduling> listAll() {
        return schedulingRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Optional<Scheduling> findById(Long id) {
        return schedulingRepository.findById(id);
    }

}
