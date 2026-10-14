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

import com.api.alquilaya.models.AlquilerModel;
import com.api.alquilaya.services.AlquilerService;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping
    public List<AlquilerModel> obtenerTodos() {
        return alquilerService.obtenerAlquileres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlquilerModel> obtenerPorId(
            @PathVariable Long id) {

        return alquilerService.obtenerAlquilerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlquilerModel> crear(
            @RequestBody AlquilerModel alquiler) {

        return ResponseEntity.ok(
                alquilerService.guardarAlquiler(alquiler)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlquilerModel> actualizar(
            @PathVariable Long id,
            @RequestBody AlquilerModel alquiler) {

        if (alquilerService.obtenerAlquilerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                alquilerService.actualizarAlquiler(id, alquiler)
        );
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<AlquilerModel> activar(
            @PathVariable Long id) {

        if (alquilerService.obtenerAlquilerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                alquilerService.activarAlquiler(id)
        );
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<AlquilerModel> completar(
            @PathVariable Long id) {

        if (alquilerService.obtenerAlquilerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                alquilerService.completarAlquiler(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (alquilerService.obtenerAlquilerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        alquilerService.eliminarAlquiler(id);

        return ResponseEntity.ok().build();
    }
}
