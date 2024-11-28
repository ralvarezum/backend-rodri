package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Caracteristica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Caracteristica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {}
