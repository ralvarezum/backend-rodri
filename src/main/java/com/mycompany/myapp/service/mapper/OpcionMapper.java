package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Opcion;
import com.mycompany.myapp.domain.Personalizacion;
import com.mycompany.myapp.service.dto.OpcionDTO;
import com.mycompany.myapp.service.dto.PersonalizacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Opcion} and its DTO {@link OpcionDTO}.
 */
@Mapper(componentModel = "spring")
public interface OpcionMapper extends EntityMapper<OpcionDTO, Opcion> {
    @Mapping(target = "personalizacion", source = "personalizacion", qualifiedByName = "personalizacionId")
    OpcionDTO toDto(Opcion s);

    @Named("personalizacionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonalizacionDTO toDtoPersonalizacionId(Personalizacion personalizacion);
}
