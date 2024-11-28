package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Dispositivo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Dispositivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {}
