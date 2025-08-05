package com.pablomoreira.recycle_scheduler.RecyclableMaterial.services;

import com.pablomoreira.recycle_scheduler.RecyclableMaterial.DTOs.RecyclableMaterialCreateDTO;
import com.pablomoreira.recycle_scheduler.RecyclableMaterial.DTOs.RecyclableMaterialDTO;
import com.pablomoreira.recycle_scheduler.RecyclableMaterial.mappers.RecyclableMaterialMapper;
import com.pablomoreira.recycle_scheduler.RecyclableMaterial.models.RecyclableMaterial;
import com.pablomoreira.recycle_scheduler.RecyclableMaterial.repositories.RecyclableMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecyclableMaterialService {

    @Autowired
    private RecyclableMaterialRepository repository;

    public List<RecyclableMaterialDTO> findAll() {
        return repository.findAll().stream()
                .map(RecyclableMaterialMapper::toDTO)
                .toList();
    }

    public RecyclableMaterialDTO create(RecyclableMaterialCreateDTO dto) {
        if (repository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Material already exists.");
        }

        RecyclableMaterial material = new RecyclableMaterial();
        material.setName(dto.getName());
        material.setDescription(dto.getDescription());
        return RecyclableMaterialMapper.toDTO(repository.save(material));
    }

    public RecyclableMaterialDTO update(Long id, RecyclableMaterialCreateDTO dto) {
        RecyclableMaterial material = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found"));

        material.setName(dto.getName());
        material.setDescription(dto.getDescription());
        return RecyclableMaterialMapper.toDTO(repository.save(material));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Material not found");
        }
        repository.deleteById(id);
    }
}
