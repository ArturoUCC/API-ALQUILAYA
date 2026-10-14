package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import com.api.alquilaya.models.MantenimientoModel;

public interface MantenimientoService {

    List<MantenimientoModel> obtenerMantenimientos();

    Optional<MantenimientoModel> obtenerMantenimientoPorId(Long id);

    MantenimientoModel guardarMantenimiento(MantenimientoModel mantenimiento);

    MantenimientoModel actualizarMantenimiento(Long id, MantenimientoModel mantenimiento);

    boolean eliminarMantenimiento(Long id);

}
