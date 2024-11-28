package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dispositivo} and its DTO {@link DispositivoDTO}.
 */
@Mapper(componentModel = "spring", uses = { CaracteristicaMapper.class, PersonalizacionMapper.class, AdicionalMapper.class })
public interface DispositivoMapper extends EntityMapper<DispositivoDTO, Dispositivo> {
    @Mapping(target = "caracteristicas", source = "caracteristicas")
    @Mapping(target = "personalizaciones", source = "personalizacions")
    @Mapping(target = "adicionales", source = "adicionals")
    DispositivoDTO toDto(Dispositivo dispositivo);

    @Mapping(target = "caracteristicas", ignore = true)
    @Mapping(target = "personalizacions", ignore = true)
    @Mapping(target = "adicionals", ignore = true)
    @Mapping(target = "removeAdicional", ignore = true)
    @Mapping(target = "removeCaracteristica", ignore = true)
    @Mapping(target = "removePersonalizacion", ignore = true)
    Dispositivo toEntity(DispositivoDTO dispositivoDTO);

    @Mapping(target = "caracteristicas", ignore = true)
    @Mapping(target = "personalizacions", ignore = true)
    @Mapping(target = "adicionals", ignore = true)
    @Mapping(target = "removeAdicional", ignore = true)
    @Mapping(target = "removeCaracteristica", ignore = true)
    @Mapping(target = "removePersonalizacion", ignore = true)
    void partialUpdate(@MappingTarget Dispositivo dispositivo, DispositivoDTO dispositivoDTO);
}
