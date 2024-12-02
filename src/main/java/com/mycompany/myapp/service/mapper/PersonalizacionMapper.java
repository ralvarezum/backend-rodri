package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.domain.Personalizacion;
import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import com.mycompany.myapp.service.dto.PersonalizacionDTO;
import com.mycompany.myapp.service.dto.VentaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Personalizacion} and its DTO {@link PersonalizacionDTO}.
 */
@Mapper(componentModel = "spring", uses = { OpcionMapper.class })
public interface PersonalizacionMapper extends EntityMapper<PersonalizacionDTO, Personalizacion> {
    @Mapping(target = "dispositivo", source = "dispositivo", qualifiedByName = "dispositivoId")
    @Mapping(target = "ventas", source = "ventas", qualifiedByName = "ventaIdSet")
    @Mapping(source = "opcions", target = "opciones")
    PersonalizacionDTO toDto(Personalizacion s);

    @Mapping(target = "ventas", ignore = true)
    @Mapping(target = "removeVenta", ignore = true)
    @Mapping(source = "opciones", target = "opcions")
    Personalizacion toEntity(PersonalizacionDTO personalizacionDTO);

    @Named("dispositivoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositivoDTO toDtoDispositivoId(Dispositivo dispositivo);

    @Named("ventaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VentaDTO toDtoVentaId(Venta venta);

    @Named("ventaIdSet")
    default Set<VentaDTO> toDtoVentaIdSet(Set<Venta> venta) {
        return venta.stream().map(this::toDtoVentaId).collect(Collectors.toSet());
    }
}
