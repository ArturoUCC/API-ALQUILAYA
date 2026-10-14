
package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import com.api.alquilaya.models.VehiculoModel;

public interface VehiculoService {

    List<VehiculoModel> obtenerVehiculos();

    Optional<VehiculoModel> obtenerVehiculoPorId(Long id);

    VehiculoModel guardarVehiculo(VehiculoModel vehiculo);

    VehiculoModel actualizarVehiculo(Long id, VehiculoModel vehiculo);

    boolean eliminarVehiculo(Long id);

}
