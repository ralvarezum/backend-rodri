package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AdicionalDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Adicional}.
 */
public interface AdicionalService {
    /**
     * Save a adicional.
     *
     * @param adicionalDTO the entity to save.
     * @return the persisted entity.
     */
    AdicionalDTO save(AdicionalDTO adicionalDTO);

    /**
     * Updates a adicional.
     *
     * @param adicionalDTO the entity to update.
     * @return the persisted entity.
     */
    AdicionalDTO update(AdicionalDTO adicionalDTO);

    /**
     * Partially updates a adicional.
     *
     * @param adicionalDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdicionalDTO> partialUpdate(AdicionalDTO adicionalDTO);

    /**
     * Get all the adicionals.
     *
     * @return the list of entities.
     */
    List<AdicionalDTO> findAll();

    /**
     * Get the "id" adicional.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdicionalDTO> findOne(Long id);

    /**
     * Delete the "id" adicional.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
