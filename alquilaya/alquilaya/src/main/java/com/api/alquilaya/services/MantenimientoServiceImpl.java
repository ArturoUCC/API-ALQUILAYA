package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.alquilaya.models.MantenimientoModel;
import com.api.alquilaya.models.VehiculoModel;
import com.api.alquilaya.repositories.IMantenimientoRepository;
import com.api.alquilaya.repositories.IVehiculoRepository;

@Service
public class MantenimientoServiceImpl implements MantenimientoService {

    @Autowired
    private IMantenimientoRepository mantenimientoRepository;

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Override
    public List<MantenimientoModel> obtenerMantenimientos() {
        return mantenimientoRepository.findAll();
    }

    @Override
    public Optional<MantenimientoModel> obtenerMantenimientoPorId(Long id) {
        return mantenimientoRepository.findById(id);
    }

    @Override
    public MantenimientoModel guardarMantenimiento(MantenimientoModel mantenimiento) {

        asignarVehiculo(mantenimiento);

        mantenimiento.setId(null);

        return mantenimientoRepository.save(mantenimiento);
    }

    @Override
    public MantenimientoModel actualizarMantenimiento(Long id, MantenimientoModel mantenimiento) {

        if (!mantenimientoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                "El mantenimiento no existe"
            );
        }

        asignarVehiculo(mantenimiento);

        mantenimiento.setId(id);

        return mantenimientoRepository.save(mantenimiento);
    }

    @Override
    public boolean eliminarMantenimiento(Long id) {

        if (mantenimientoRepository.existsById(id)) {
            mantenimientoRepository.deleteById(id);
            return true;
        }

        return false;
    }

    private void asignarVehiculo(MantenimientoModel mantenimiento) {

        if (mantenimiento.getVehiculo() == null ||
            mantenimiento.getVehiculo().getId() == null) {

            throw new IllegalArgumentException(
                "Debes indicar un vehiculo"
            );
        }

        Long vehiculoId = mantenimiento.getVehiculo().getId();

        VehiculoModel vehiculo = vehiculoRepository.findById(vehiculoId)
            .orElseThrow(() -> new IllegalArgumentException(
                "El vehiculo no existe"
            ));

        mantenimiento.setVehiculo(vehiculo);
    }
}
