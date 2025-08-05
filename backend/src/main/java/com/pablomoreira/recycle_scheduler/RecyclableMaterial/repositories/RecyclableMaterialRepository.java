package com.pablomoreira.recycle_scheduler.RecyclableMaterial.repositories;

import com.pablomoreira.recycle_scheduler.RecyclableMaterial.models.RecyclableMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecyclableMaterialRepository extends JpaRepository<RecyclableMaterial, Long> {
    boolean existsByName(String name);
}
