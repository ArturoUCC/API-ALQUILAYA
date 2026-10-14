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

import com.api.alquilaya.models.ClienteModel;
import com.api.alquilaya.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<ClienteModel> obtenerTodos() {
        return clienteService.obtenerClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> obtenerPorId(@PathVariable Long id) {
        return clienteService.obtenerClientePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteModel> crear(@RequestBody ClienteModel cliente) {
        return ResponseEntity.ok(
                clienteService.guardarCliente(cliente)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> actualizar(
            @PathVariable Long id,
            @RequestBody ClienteModel cliente) {

        if (clienteService.obtenerClientePorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                clienteService.actualizarCliente(id, cliente)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (clienteService.obtenerClientePorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        clienteService.eliminarCliente(id);

        return ResponseEntity.ok().build();
    }
}
