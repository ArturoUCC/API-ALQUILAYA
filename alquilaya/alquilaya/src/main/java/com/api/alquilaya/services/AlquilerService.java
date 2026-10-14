
package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import com.api.alquilaya.models.AlquilerModel;

public interface AlquilerService {

    List<AlquilerModel> obtenerAlquileres();

    Optional<AlquilerModel> obtenerAlquilerPorId(Long id);

    AlquilerModel guardarAlquiler(AlquilerModel alquiler);

    AlquilerModel actualizarAlquiler(Long id, AlquilerModel alquiler);

    AlquilerModel activarAlquiler(Long id);

    AlquilerModel completarAlquiler(Long id);

    boolean eliminarAlquiler(Long id);

}
