package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Adicional} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdicionalDTO implements Serializable {

    private Long id;

    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    private BigDecimal precioGratis;

    private Long idCatedra;

    private DispositivoDTO dispositivo;

    private Set<VentaDTO> ventas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getPrecioGratis() {
        return precioGratis;
    }

    public void setPrecioGratis(BigDecimal precioGratis) {
        this.precioGratis = precioGratis;
    }

    public Long getIdCatedra() {
        return idCatedra;
    }

    public void setIdCatedra(Long idCatedra) {
        this.idCatedra = idCatedra;
    }

    public DispositivoDTO getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(DispositivoDTO dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Set<VentaDTO> getVentas() {
        return ventas;
    }

    public void setVentas(Set<VentaDTO> ventas) {
        this.ventas = ventas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdicionalDTO)) {
            return false;
        }

        AdicionalDTO adicionalDTO = (AdicionalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adicionalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdicionalDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precio=" + getPrecio() +
            ", precioGratis=" + getPrecioGratis() +
            ", idCatedra=" + getIdCatedra() +
            ", dispositivo=" + getDispositivo() +
            ", ventas=" + getVentas() +
            "}";
    }
}
