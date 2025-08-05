package com.pablomoreira.recycle_scheduler.Scheduling.repositories;

import com.pablomoreira.recycle_scheduler.Scheduling.models.Scheduling;
import com.pablomoreira.recycle_scheduler.Scheduling.models.SchedulingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    List<Scheduling> findAll();

    Optional<Scheduling> findByCodeProtocol(String codeProtocol);


}
