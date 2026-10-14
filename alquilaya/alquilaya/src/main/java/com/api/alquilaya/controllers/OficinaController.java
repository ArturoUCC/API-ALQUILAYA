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

import com.api.alquilaya.models.OficinaModel;
import com.api.alquilaya.services.OficinaService;

@RestController
@RequestMapping("/api/oficinas")
public class OficinaController {

    @Autowired
    private OficinaService oficinaService;

    @GetMapping
    public List<OficinaModel> obtenerTodas() {
        return oficinaService.obtenerOficinas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OficinaModel> obtenerPorId(@PathVariable Long id) {
        return oficinaService.obtenerOficinaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OficinaModel> crear(@RequestBody OficinaModel oficina) {
        return ResponseEntity.ok(oficinaService.guardarOficina(oficina));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OficinaModel> actualizar(
            @PathVariable Long id,
            @RequestBody OficinaModel oficina) {

        if (oficinaService.obtenerOficinaPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                oficinaService.actualizarOficina(id, oficina)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (oficinaService.obtenerOficinaPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        oficinaService.eliminarOficina(id);

        return ResponseEntity.ok().build();
    }
}
