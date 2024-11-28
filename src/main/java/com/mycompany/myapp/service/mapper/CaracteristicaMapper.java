package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Caracteristica;
import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.service.dto.CaracteristicaDTO;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Caracteristica} and its DTO
 * {@link CaracteristicaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CaracteristicaMapper extends EntityMapper<CaracteristicaDTO, Caracteristica> {
    @Mapping(target = "dispositivo", source = "dispositivo", qualifiedByName = "dispositivoId")
    CaracteristicaDTO toDto(Caracteristica s);

    @Named("dispositivoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositivoDTO toDtoDispositivoId(Dispositivo dispositivo);
}
