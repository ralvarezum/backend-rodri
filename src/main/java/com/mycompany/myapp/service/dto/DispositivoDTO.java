package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Moneda;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Dispositivo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DispositivoDTO implements Serializable {

    private Long id;

    private String codigo;

    private String nombre;

    private String descripcion;

    private BigDecimal precioBase;

    private Moneda moneda;

    private Long idCatedra;

    private List<CaracteristicaDTO> caracteristicas;

    private List<PersonalizacionDTO> personalizaciones;

    private List<AdicionalDTO> adicionales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Long getIdCatedra() {
        return idCatedra;
    }

    public void setIdCatedra(Long idCatedra) {
        this.idCatedra = idCatedra;
    }

    public List<CaracteristicaDTO> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<CaracteristicaDTO> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public List<PersonalizacionDTO> getPersonalizaciones() {
        return personalizaciones;
    }

    public void setPersonalizaciones(List<PersonalizacionDTO> personalizaciones) {
        this.personalizaciones = personalizaciones;
    }

    public List<AdicionalDTO> getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(List<AdicionalDTO> adicionales) {
        this.adicionales = adicionales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DispositivoDTO)) {
            return false;
        }

        DispositivoDTO dispositivoDTO = (DispositivoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dispositivoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DispositivoDTO{" +
                "id=" + getId() +
                ", codigo='" + getCodigo() + "'" +
                ", nombre='" + getNombre() + "'" +
                ", descripcion='" + getDescripcion() + "'" +
                ", precioBase=" + getPrecioBase() +
                ", moneda='" + getMoneda() + "'" +
                ", idCatedra=" + getIdCatedra() +
                "}";
    }
}
