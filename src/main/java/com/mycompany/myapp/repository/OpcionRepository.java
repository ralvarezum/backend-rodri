package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Opcion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Opcion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpcionRepository extends JpaRepository<Opcion, Long> {}
