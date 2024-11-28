package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.OpcionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Opcion}.
 */
public interface OpcionService {
    /**
     * Save a opcion.
     *
     * @param opcionDTO the entity to save.
     * @return the persisted entity.
     */
    OpcionDTO save(OpcionDTO opcionDTO);

    /**
     * Updates a opcion.
     *
     * @param opcionDTO the entity to update.
     * @return the persisted entity.
     */
    OpcionDTO update(OpcionDTO opcionDTO);

    /**
     * Partially updates a opcion.
     *
     * @param opcionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OpcionDTO> partialUpdate(OpcionDTO opcionDTO);

    /**
     * Get all the opcions.
     *
     * @return the list of entities.
     */
    List<OpcionDTO> findAll();

    /**
     * Get the "id" opcion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OpcionDTO> findOne(Long id);

    /**
     * Delete the "id" opcion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
