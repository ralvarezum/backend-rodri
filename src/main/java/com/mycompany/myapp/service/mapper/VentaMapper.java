package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Adicional;
import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.domain.Personalizacion;
import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.service.dto.AdicionalDTO;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import com.mycompany.myapp.service.dto.PersonalizacionDTO;
import com.mycompany.myapp.service.dto.VentaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venta} and its DTO {@link VentaDTO}.
 */
@Mapper(componentModel = "spring")
public interface VentaMapper extends EntityMapper<VentaDTO, Venta> {
    @Mapping(target = "dispositivo", source = "dispositivo", qualifiedByName = "dispositivoId")
    @Mapping(target = "adicionals", source = "adicionals", qualifiedByName = "adicionalIdSet")
    @Mapping(target = "personalizacions", source = "personalizacions", qualifiedByName = "personalizacionIdSet")
    VentaDTO toDto(Venta s);

    @Mapping(target = "removeAdicional", ignore = true)
    @Mapping(target = "removePersonalizacion", ignore = true)
    Venta toEntity(VentaDTO ventaDTO);

    @Named("dispositivoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositivoDTO toDtoDispositivoId(Dispositivo dispositivo);

    @Named("adicionalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdicionalDTO toDtoAdicionalId(Adicional adicional);

    @Named("adicionalIdSet")
    default Set<AdicionalDTO> toDtoAdicionalIdSet(Set<Adicional> adicional) {
        return adicional.stream().map(this::toDtoAdicionalId).collect(Collectors.toSet());
    }

    @Named("personalizacionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonalizacionDTO toDtoPersonalizacionId(Personalizacion personalizacion);

    @Named("personalizacionIdSet")
    default Set<PersonalizacionDTO> toDtoPersonalizacionIdSet(Set<Personalizacion> personalizacion) {
        return personalizacion.stream().map(this::toDtoPersonalizacionId).collect(Collectors.toSet());
    }
}
