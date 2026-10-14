package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import com.api.alquilaya.models.ClienteModel;

public interface ClienteService {

    List<ClienteModel> obtenerClientes();

    Optional<ClienteModel> obtenerClientePorId(Long id);

    ClienteModel guardarCliente(ClienteModel cliente);

    ClienteModel actualizarCliente(Long id, ClienteModel cliente);

    boolean eliminarCliente(Long id);

}
