package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DispositivoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing
 * {@link com.mycompany.myapp.domain.Dispositivo}.
 */
public interface DispositivoService {
    /**
     * Save a dispositivo.
     *
     * @param dispositivoDTO the entity to save.
     * @return the persisted entity.
     */
    DispositivoDTO save(DispositivoDTO dispositivoDTO);

    /**
     * Updates a dispositivo.
     *
     * @param dispositivoDTO the entity to update.
     * @return the persisted entity.
     */
    DispositivoDTO update(DispositivoDTO dispositivoDTO);

    /**
     * Partially updates a dispositivo.
     *
     * @param dispositivoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DispositivoDTO> partialUpdate(DispositivoDTO dispositivoDTO);

    /**
     * Get all the dispositivos.
     *
     * @return the list of entities.
     */
    List<DispositivoDTO> findAll();

    /**
     * Get the "id" dispositivo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DispositivoDTO> findOne(Long id);

    /**
     * Delete the "id" dispositivo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<DispositivoDTO> fetchDispositivos();
}
