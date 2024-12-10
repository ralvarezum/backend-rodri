package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.domain.Personalizacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Personalizacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalizacionRepository extends JpaRepository<Personalizacion, Long> {
    Optional<Personalizacion> findByIdCatedra(Long idCatedra);
}
