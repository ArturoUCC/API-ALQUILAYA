
package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import com.api.alquilaya.models.OficinaModel;

public interface OficinaService {

    List<OficinaModel> obtenerOficinas();

    Optional<OficinaModel> obtenerOficinaPorId(Long id);

    OficinaModel guardarOficina(OficinaModel oficina);

    OficinaModel actualizarOficina(Long id, OficinaModel oficina);

    boolean eliminarOficina(Long id);

}
