
package com.api.alquilaya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.alquilaya.models.VehiculoModel;

public interface IVehiculoRepository extends JpaRepository<VehiculoModel, Long> {

    boolean existsByOficinaId(Long oficinaId);

}
