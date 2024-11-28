package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Caracteristica} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CaracteristicaDTO implements Serializable {

    private Long id;

    private String nombre;

    private String descripcion;

    private Long idCatedra;

    private DispositivoDTO dispositivo;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CaracteristicaDTO)) {
            return false;
        }

        CaracteristicaDTO caracteristicaDTO = (CaracteristicaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, caracteristicaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CaracteristicaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", idCatedra=" + getIdCatedra() +
            ", dispositivo=" + getDispositivo() +
            "}";
    }
}
