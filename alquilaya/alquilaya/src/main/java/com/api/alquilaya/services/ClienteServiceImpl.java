package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.alquilaya.models.ClienteModel;
import com.api.alquilaya.repositories.IClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public List<ClienteModel> obtenerClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<ClienteModel> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public ClienteModel guardarCliente(ClienteModel cliente) {
        cliente.setId(null);
        return clienteRepository.save(cliente);
    }

    @Override
    public ClienteModel actualizarCliente(Long id, ClienteModel cliente) {

        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException(
                "El cliente no existe"
            );
        }

        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    @Override
    public boolean eliminarCliente(Long id) {

        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
