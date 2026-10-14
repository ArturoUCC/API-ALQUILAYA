package com.api.alquilaya.services;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.alquilaya.models.AlquilerModel;
import com.api.alquilaya.models.ClienteModel;
import com.api.alquilaya.models.VehiculoModel;
import com.api.alquilaya.repositories.IAlquilerRepository;
import com.api.alquilaya.repositories.IClienteRepository;
import com.api.alquilaya.repositories.IVehiculoRepository;

@Service
public class AlquilerServiceImpl implements AlquilerService {

    @Autowired
    private IAlquilerRepository alquilerRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Override
    public List<AlquilerModel> obtenerAlquileres() {
        return alquilerRepository.findAll();
    }

    @Override
    public Optional<AlquilerModel> obtenerAlquilerPorId(Long id) {
        return alquilerRepository.findById(id);
    }

    @Override
    @Transactional
    public AlquilerModel guardarAlquiler(AlquilerModel alquiler) {

        validarDatos(alquiler);
        asignarClienteYVehiculo(alquiler);
        validarDisponibilidad(alquiler, null);

        alquiler.setId(null);
        alquiler.setEstado("Pendiente");

        calcularCosto(alquiler);

        return alquilerRepository.save(alquiler);
    }

    @Override
    @Transactional
    public AlquilerModel actualizarAlquiler(Long id, AlquilerModel alquiler) {

        AlquilerModel existente = alquilerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                "El alquiler no existe"
            ));

        if (!existente.getEstado().equals("Pendiente")) {
            throw new IllegalArgumentException(
                "Solo se pueden modificar alquileres pendientes"
            );
        }

        validarDatos(alquiler);
        asignarClienteYVehiculo(alquiler);
        validarDisponibilidad(alquiler, id);

        alquiler.setId(id);
        alquiler.setEstado("Pendiente");

        calcularCosto(alquiler);

        return alquilerRepository.save(alquiler);
    }

    @Override
    @Transactional
    public AlquilerModel activarAlquiler(Long id) {

        AlquilerModel alquiler = alquilerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                "El alquiler no existe"
            ));

        if (!alquiler.getEstado().equals("Pendiente")) {
            throw new IllegalArgumentException(
                "El alquiler debe estar pendiente"
            );
        }

        VehiculoModel vehiculo = alquiler.getVehiculo();

        if (!vehiculo.getEstado().equals("Disponible")) {
            throw new IllegalArgumentException(
                "El vehiculo no esta disponible"
            );
        }

        validarDisponibilidad(alquiler, id);

        alquiler.setEstado("Activo");
        vehiculo.setEstado("Alquilado");

        vehiculoRepository.save(vehiculo);

        return alquilerRepository.save(alquiler);
    }

    @Override
    @Transactional
    public AlquilerModel completarAlquiler(Long id) {

        AlquilerModel alquiler = alquilerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                "El alquiler no existe"
            ));

        if (!alquiler.getEstado().equals("Activo")) {
            throw new IllegalArgumentException(
                "El alquiler debe estar activo"
            );
        }

        alquiler.setEstado("Completado");

        VehiculoModel vehiculo = alquiler.getVehiculo();
        vehiculo.setEstado("Disponible");

        vehiculoRepository.save(vehiculo);

        return alquilerRepository.save(alquiler);
    }

    @Override
    @Transactional
    public boolean eliminarAlquiler(Long id) {

        Optional<AlquilerModel> alquiler = alquilerRepository.findById(id);

        if (alquiler.isEmpty()) {
            return false;
        }

        if (!alquiler.get().getEstado().equals("Pendiente")) {
            throw new IllegalArgumentException(
                "Solo se pueden eliminar alquileres pendientes"
            );
        }

        alquilerRepository.deleteById(id);
        return true;
    }

    private void validarDatos(AlquilerModel alquiler) {

        if (alquiler.getFechaInicio() == null ||
            alquiler.getFechaFin() == null) {
            throw new IllegalArgumentException(
                "Debes indicar las fechas del alquiler"
            );
        }

        if (alquiler.getFechaFin().isBefore(alquiler.getFechaInicio())) {
            throw new IllegalArgumentException(
                "La fecha final no puede ser anterior a la inicial"
            );
        }

        if (alquiler.getCliente() == null ||
            alquiler.getCliente().getId() == null) {
            throw new IllegalArgumentException(
                "Debes indicar un cliente"
            );
        }

        if (alquiler.getVehiculo() == null ||
            alquiler.getVehiculo().getId() == null) {
            throw new IllegalArgumentException(
                "Debes indicar un vehiculo"
            );
        }
    }

    private void asignarClienteYVehiculo(AlquilerModel alquiler) {

        Long clienteId = alquiler.getCliente().getId();
        Long vehiculoId = alquiler.getVehiculo().getId();

        ClienteModel cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new IllegalArgumentException(
                "El cliente no existe"
            ));

        VehiculoModel vehiculo = vehiculoRepository.findById(vehiculoId)
            .orElseThrow(() -> new IllegalArgumentException(
                "El vehiculo no existe"
            ));

        if (vehiculo.getEstado().equals("Mantenimiento")) {
            throw new IllegalArgumentException(
                "El vehiculo esta en mantenimiento"
            );
        }

        alquiler.setCliente(cliente);
        alquiler.setVehiculo(vehiculo);
    }

    private void validarDisponibilidad(AlquilerModel alquiler, Long excluirId) {

        List<AlquilerModel> reservas = alquilerRepository
            .findByVehiculoId(alquiler.getVehiculo().getId());

        for (AlquilerModel reserva : reservas) {

            if (excluirId != null && reserva.getId().equals(excluirId)) {
                continue;
            }

            if (reserva.getEstado().equals("Completado")) {
                continue;
            }

            boolean coincidenFechas =
                !alquiler.getFechaInicio().isAfter(reserva.getFechaFin()) &&
                !alquiler.getFechaFin().isBefore(reserva.getFechaInicio());

            if (coincidenFechas) {
                throw new IllegalArgumentException(
                    "El vehiculo ya tiene un alquiler en esas fechas"
                );
            }
        }
    }

    private void calcularCosto(AlquilerModel alquiler) {

        long dias = ChronoUnit.DAYS.between(
            alquiler.getFechaInicio(),
            alquiler.getFechaFin()
        );

        if (dias == 0) {
            dias = 1;
        }

        double total = dias * alquiler.getVehiculo().getTarifaDiaria();

        alquiler.setCostoTotal(total);
    }
}
