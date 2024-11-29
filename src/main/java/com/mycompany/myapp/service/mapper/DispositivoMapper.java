package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dispositivo} and its DTO {@link DispositivoDTO}.
 */
@Mapper(componentModel = "spring", uses = { CaracteristicaMapper.class, PersonalizacionMapper.class, AdicionalMapper.class })
public interface DispositivoMapper extends EntityMapper<DispositivoDTO, Dispositivo> {
    @Mapping(source = "caracteristicas", target = "caracteristicas")
    @Mapping(source = "personalizacions", target = "personalizaciones")
    @Mapping(source = "adicionals", target = "adicionales")
    DispositivoDTO toDto(Dispositivo dispositivo);

    @Mapping(source = "caracteristicas", target = "caracteristicas")
    @Mapping(source = "personalizaciones", target = "personalizacions")
    @Mapping(source = "adicionales", target = "adicionals")
    Dispositivo toEntity(DispositivoDTO dispositivoDTO);
}
