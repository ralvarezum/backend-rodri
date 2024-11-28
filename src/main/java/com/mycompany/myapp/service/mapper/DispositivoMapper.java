package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dispositivo} and its DTO {@link DispositivoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DispositivoMapper extends EntityMapper<DispositivoDTO, Dispositivo> {}
