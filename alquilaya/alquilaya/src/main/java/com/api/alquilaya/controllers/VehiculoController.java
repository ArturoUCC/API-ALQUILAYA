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

import com.api.alquilaya.models.VehiculoModel;
import com.api.alquilaya.services.VehiculoService;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public List<VehiculoModel> obtenerTodos() {
        return vehiculoService.obtenerVehiculos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoModel> obtenerPorId(@PathVariable Long id) {
        return vehiculoService.obtenerVehiculoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VehiculoModel> crear(@RequestBody VehiculoModel vehiculo) {
        return ResponseEntity.ok(
                vehiculoService.guardarVehiculo(vehiculo)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoModel> actualizar(
            @PathVariable Long id,
            @RequestBody VehiculoModel vehiculo) {

        if (vehiculoService.obtenerVehiculoPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                vehiculoService.actualizarVehiculo(id, vehiculo)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (vehiculoService.obtenerVehiculoPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        vehiculoService.eliminarVehiculo(id);

        return ResponseEntity.ok().build();
    }
}
