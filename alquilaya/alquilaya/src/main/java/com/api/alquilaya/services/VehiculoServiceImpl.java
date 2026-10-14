
package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.alquilaya.models.OficinaModel;
import com.api.alquilaya.models.VehiculoModel;
import com.api.alquilaya.repositories.IOficinaRepository;
import com.api.alquilaya.repositories.IVehiculoRepository;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Autowired
    private IOficinaRepository oficinaRepository;

    @Override
    public List<VehiculoModel> obtenerVehiculos() {
        return vehiculoRepository.findAll();
    }

    @Override
    public Optional<VehiculoModel> obtenerVehiculoPorId(Long id) {
        return vehiculoRepository.findById(id);
    }

    @Override
    public VehiculoModel guardarVehiculo(VehiculoModel vehiculo) {

        asignarOficina(vehiculo);
        validarEstado(vehiculo.getEstado());

        vehiculo.setId(null);

        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public VehiculoModel actualizarVehiculo(Long id, VehiculoModel vehiculo) {

        if (!vehiculoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                "El vehiculo no existe"
            );
        }

        asignarOficina(vehiculo);
        validarEstado(vehiculo.getEstado());

        vehiculo.setId(id);

        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public boolean eliminarVehiculo(Long id) {

        if (vehiculoRepository.existsById(id)) {
            vehiculoRepository.deleteById(id);
            return true;
        }

        return false;
    }

    private void asignarOficina(VehiculoModel vehiculo) {

        if (vehiculo.getOficina() == null ||
            vehiculo.getOficina().getId() == null) {

            throw new IllegalArgumentException(
                "Debes indicar una oficina"
            );
        }

        Long oficinaId = vehiculo.getOficina().getId();

        OficinaModel oficina = oficinaRepository.findById(oficinaId)
            .orElseThrow(() -> new IllegalArgumentException(
                "La oficina no existe"
            ));

        vehiculo.setOficina(oficina);
    }

    private void validarEstado(String estado) {

        if (estado == null ||
            (!estado.equals("Disponible") &&
             !estado.equals("Alquilado") &&
             !estado.equals("Mantenimiento"))) {

            throw new IllegalArgumentException(
                "Estado invalido. Usa Disponible, Alquilado o Mantenimiento"
            );
        }
    }
}
