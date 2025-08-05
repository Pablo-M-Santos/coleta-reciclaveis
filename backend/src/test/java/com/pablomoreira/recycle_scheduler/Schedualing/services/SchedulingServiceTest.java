package com.pablomoreira.recycle_scheduler.Schedualing.services;

import com.pablomoreira.recycle_scheduler.Scheduling.DTOs.SchedulingDetailDTO;
import com.pablomoreira.recycle_scheduler.Scheduling.DTOs.SchedulingStatusUpdateDTO;
import com.pablomoreira.recycle_scheduler.Scheduling.models.Scheduling;
import com.pablomoreira.recycle_scheduler.Scheduling.models.SchedulingStatus;
import com.pablomoreira.recycle_scheduler.Scheduling.repositories.SchedulingRepository;
import com.pablomoreira.recycle_scheduler.Scheduling.services.SchedulingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchedulingServiceTest {

    @InjectMocks
    private SchedulingService schedulingService;

    @Mock
    private SchedulingRepository schedulingRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateStatus_shouldUpdateStatusSuccessfully() {
        // Arrange
        Scheduling scheduling = new Scheduling();
        scheduling.setId(1L);
        scheduling.setStatus(SchedulingStatus.PENDING);
        scheduling.setUpdatedAt(LocalDateTime.now().minusDays(1));

        SchedulingStatusUpdateDTO dto = new SchedulingStatusUpdateDTO();
        dto.setStatus(SchedulingStatus.SCHEDULED);
        dto.setJustification(null);

        when(schedulingRepository.findById(1L)).thenReturn(Optional.of(scheduling));
        when(schedulingRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // Act
        SchedulingDetailDTO updatedDto = schedulingService.updateStatus(1L, dto);

        // Assert
        assertEquals(SchedulingStatus.SCHEDULED.name(), updatedDto.getStatus());
        assertNull(updatedDto.getJustification());
        assertNotNull(updatedDto.getUpdatedAt());
        verify(schedulingRepository).save(any());
    }

    @Test
    void updateStatus_shouldThrowExceptionWhenSchedulingNotFound() {
        when(schedulingRepository.findById(999L)).thenReturn(Optional.empty());

        SchedulingStatusUpdateDTO dto = new SchedulingStatusUpdateDTO();
        dto.setStatus(SchedulingStatus.COMPLETED);
        dto.setJustification("Done");

        assertThrows(EntityNotFoundException.class, () -> schedulingService.updateStatus(999L, dto));
    }

    @Test
    void updateStatus_shouldThrowExceptionWhenJustificationMissingForCompleted() {
        Scheduling scheduling = new Scheduling();
        scheduling.setId(1L);

        SchedulingStatusUpdateDTO dto = new SchedulingStatusUpdateDTO();
        dto.setStatus(SchedulingStatus.COMPLETED);
        dto.setJustification(" "); // blank justification

        when(schedulingRepository.findById(1L)).thenReturn(Optional.of(scheduling));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> schedulingService.updateStatus(1L, dto));
        assertEquals("Justification is mandatory for Completed or Cancelled status.", ex.getMessage());
    }

    @Test
    void updateStatus_shouldThrowExceptionWhenJustificationMissingForCanceled() {
        Scheduling scheduling = new Scheduling();
        scheduling.setId(1L);

        SchedulingStatusUpdateDTO dto = new SchedulingStatusUpdateDTO();
        dto.setStatus(SchedulingStatus.CANCELED);
        dto.setJustification(null); // no justification

        when(schedulingRepository.findById(1L)).thenReturn(Optional.of(scheduling));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> schedulingService.updateStatus(1L, dto));
        assertEquals("Justification is mandatory for Completed or Cancelled status.", ex.getMessage());
    }

    @Test
    void updateStatus_shouldAcceptJustificationWhenRequired() {
        Scheduling scheduling = new Scheduling();
        scheduling.setId(1L);

        SchedulingStatusUpdateDTO dto = new SchedulingStatusUpdateDTO();
        dto.setStatus(SchedulingStatus.CANCELED);
        dto.setJustification("Client canceled");

        when(schedulingRepository.findById(1L)).thenReturn(Optional.of(scheduling));
        when(schedulingRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        SchedulingDetailDTO updated = schedulingService.updateStatus(1L, dto);

        assertEquals("Client canceled", updated.getJustification());
        assertEquals(SchedulingStatus.CANCELED.name(), updated.getStatus());
        assertNotNull(updated.getUpdatedAt());
    }
}

