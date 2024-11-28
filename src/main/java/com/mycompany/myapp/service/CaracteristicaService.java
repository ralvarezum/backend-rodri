package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CaracteristicaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Caracteristica}.
 */
public interface CaracteristicaService {
    /**
     * Save a caracteristica.
     *
     * @param caracteristicaDTO the entity to save.
     * @return the persisted entity.
     */
    CaracteristicaDTO save(CaracteristicaDTO caracteristicaDTO);

    /**
     * Updates a caracteristica.
     *
     * @param caracteristicaDTO the entity to update.
     * @return the persisted entity.
     */
    CaracteristicaDTO update(CaracteristicaDTO caracteristicaDTO);

    /**
     * Partially updates a caracteristica.
     *
     * @param caracteristicaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CaracteristicaDTO> partialUpdate(CaracteristicaDTO caracteristicaDTO);

    /**
     * Get all the caracteristicas.
     *
     * @return the list of entities.
     */
    List<CaracteristicaDTO> findAll();

    /**
     * Get the "id" caracteristica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CaracteristicaDTO> findOne(Long id);

    /**
     * Delete the "id" caracteristica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
