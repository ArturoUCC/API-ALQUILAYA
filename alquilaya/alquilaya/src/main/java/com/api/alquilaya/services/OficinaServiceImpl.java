
package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.alquilaya.models.OficinaModel;
import com.api.alquilaya.repositories.IOficinaRepository;
import com.api.alquilaya.repositories.IVehiculoRepository;

@Service
public class OficinaServiceImpl implements OficinaService {

    @Autowired
    private IOficinaRepository oficinaRepository;

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Override
    public List<OficinaModel> obtenerOficinas() {
        return oficinaRepository.findAll();
    }

    @Override
    public Optional<OficinaModel> obtenerOficinaPorId(Long id) {
        return oficinaRepository.findById(id);
    }

    @Override
    public OficinaModel guardarOficina(OficinaModel oficina) {
        oficina.setId(null);
        return oficinaRepository.save(oficina);
    }

    @Override
    public OficinaModel actualizarOficina(Long id, OficinaModel oficina) {

        if (!oficinaRepository.existsById(id)) {
            throw new IllegalArgumentException(
                "La oficina no existe"
            );
        }

        oficina.setId(id);
        return oficinaRepository.save(oficina);
    }

    @Override
    public boolean eliminarOficina(Long id) {

        if (!oficinaRepository.existsById(id)) {
            return false;
        }

        boolean tieneVehiculos = vehiculoRepository
                .existsByOficinaId(id);

        if (tieneVehiculos) {
            throw new IllegalArgumentException(
                "No puedes eliminar una oficina que tiene vehiculos asignados"
            );
        }

        oficinaRepository.deleteById(id);
        return true;
    }
}
