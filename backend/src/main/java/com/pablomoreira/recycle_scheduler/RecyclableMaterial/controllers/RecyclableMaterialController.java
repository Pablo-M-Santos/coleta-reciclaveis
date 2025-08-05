package com.pablomoreira.recycle_scheduler.RecyclableMaterial.controllers;

import com.pablomoreira.recycle_scheduler.RecyclableMaterial.DTOs.RecyclableMaterialCreateDTO;
import com.pablomoreira.recycle_scheduler.RecyclableMaterial.DTOs.RecyclableMaterialDTO;
import com.pablomoreira.recycle_scheduler.RecyclableMaterial.services.RecyclableMaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiais")
public class RecyclableMaterialController {

    @Autowired
    private RecyclableMaterialService recyclableMaterialService;

    @GetMapping
    public ResponseEntity<List<RecyclableMaterialDTO>> list() {
        return ResponseEntity.ok(recyclableMaterialService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RecyclableMaterialDTO> create(@RequestBody @Valid RecyclableMaterialCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recyclableMaterialService.create(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RecyclableMaterialDTO> update(@PathVariable Long id, @RequestBody @Valid RecyclableMaterialCreateDTO dto) {
        return ResponseEntity.ok(recyclableMaterialService.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recyclableMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

