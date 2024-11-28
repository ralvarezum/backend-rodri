package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Venta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VentaDTO implements Serializable {

    private Long id;

    private ZonedDateTime fechaVenta;

    private BigDecimal precioFinal;

    @NotNull
    private DispositivoDTO dispositivo;

    private Set<AdicionalDTO> adicionals = new HashSet<>();

    private Set<PersonalizacionDTO> personalizacions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(ZonedDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(BigDecimal precioFinal) {
        this.precioFinal = precioFinal;
    }

    public DispositivoDTO getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(DispositivoDTO dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Set<AdicionalDTO> getAdicionals() {
        return adicionals;
    }

    public void setAdicionals(Set<AdicionalDTO> adicionals) {
        this.adicionals = adicionals;
    }

    public Set<PersonalizacionDTO> getPersonalizacions() {
        return personalizacions;
    }

    public void setPersonalizacions(Set<PersonalizacionDTO> personalizacions) {
        this.personalizacions = personalizacions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VentaDTO)) {
            return false;
        }

        VentaDTO ventaDTO = (VentaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ventaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VentaDTO{" +
            "id=" + getId() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", precioFinal=" + getPrecioFinal() +
            ", dispositivo=" + getDispositivo() +
            ", adicionals=" + getAdicionals() +
            ", personalizacions=" + getPersonalizacions() +
            "}";
    }
}
