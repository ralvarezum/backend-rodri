package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Adicional;
import com.mycompany.myapp.domain.Dispositivo;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Adicional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdicionalRepository extends JpaRepository<Adicional, Long> {
    Optional<Adicional> findByIdCatedra(Long idCatedra);
}
