package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Caracteristica;
import com.mycompany.myapp.domain.Dispositivo;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Caracteristica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
    Optional<Caracteristica> findByIdCatedra(Long idCatedra);
}
