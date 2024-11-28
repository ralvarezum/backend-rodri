package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PersonalizacionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Personalizacion}.
 */
public interface PersonalizacionService {
    /**
     * Save a personalizacion.
     *
     * @param personalizacionDTO the entity to save.
     * @return the persisted entity.
     */
    PersonalizacionDTO save(PersonalizacionDTO personalizacionDTO);

    /**
     * Updates a personalizacion.
     *
     * @param personalizacionDTO the entity to update.
     * @return the persisted entity.
     */
    PersonalizacionDTO update(PersonalizacionDTO personalizacionDTO);

    /**
     * Partially updates a personalizacion.
     *
     * @param personalizacionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonalizacionDTO> partialUpdate(PersonalizacionDTO personalizacionDTO);

    /**
     * Get all the personalizacions.
     *
     * @return the list of entities.
     */
    List<PersonalizacionDTO> findAll();

    /**
     * Get the "id" personalizacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonalizacionDTO> findOne(Long id);

    /**
     * Delete the "id" personalizacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
