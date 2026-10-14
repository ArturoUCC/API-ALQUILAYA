package com.api.alquilaya.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.alquilaya.models.MantenimientoModel;
import com.api.alquilaya.services.MantenimientoService;

@RestController
@RequestMapping("/api/mantenimientos")
public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    @GetMapping
    public List<MantenimientoModel> obtenerTodos() {
        return mantenimientoService.obtenerMantenimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MantenimientoModel> obtenerPorId(
            @PathVariable Long id) {

        return mantenimientoService.obtenerMantenimientoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MantenimientoModel> crear(
            @RequestBody MantenimientoModel mantenimiento) {

        return ResponseEntity.ok(
                mantenimientoService.guardarMantenimiento(mantenimiento)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MantenimientoModel> actualizar(
            @PathVariable Long id,
            @RequestBody MantenimientoModel mantenimiento) {

        if (mantenimientoService.obtenerMantenimientoPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                mantenimientoService.actualizarMantenimiento(id, mantenimiento)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (mantenimientoService.obtenerMantenimientoPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        mantenimientoService.eliminarMantenimiento(id);

        return ResponseEntity.ok().build();
    }
}
