package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Personalizacion.
 */
@Entity
@Table(name = "personalizacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Personalizacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "id_catedra")
    private Long idCatedra;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "personalizacion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personalizacion" }, allowSetters = true)
    private Set<Opcion> opcions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "adicionals", "caracteristicas", "personalizacions" }, allowSetters = true)
    private Dispositivo dispositivo;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "personalizacions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dispositivo", "adicionals", "personalizacions" }, allowSetters = true)
    private Set<Venta> ventas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Personalizacion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Personalizacion nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Personalizacion descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdCatedra() {
        return this.idCatedra;
    }

    public Personalizacion idCatedra(Long idCatedra) {
        this.setIdCatedra(idCatedra);
        return this;
    }

    public void setIdCatedra(Long idCatedra) {
        this.idCatedra = idCatedra;
    }

    public Set<Opcion> getOpcions() {
        return this.opcions;
    }

    public void setOpcions(Set<Opcion> opcions) {
        if (this.opcions != null) {
            this.opcions.forEach(i -> i.setPersonalizacion(null));
        }
        if (opcions != null) {
            opcions.forEach(i -> i.setPersonalizacion(this));
        }
        this.opcions = opcions;
    }

    public Personalizacion opcions(Set<Opcion> opcions) {
        this.setOpcions(opcions);
        return this;
    }

    public Personalizacion addOpcion(Opcion opcion) {
        this.opcions.add(opcion);
        opcion.setPersonalizacion(this);
        return this;
    }

    public Personalizacion removeOpcion(Opcion opcion) {
        this.opcions.remove(opcion);
        opcion.setPersonalizacion(null);
        return this;
    }

    public Dispositivo getDispositivo() {
        return this.dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Personalizacion dispositivo(Dispositivo dispositivo) {
        this.setDispositivo(dispositivo);
        return this;
    }

    public Set<Venta> getVentas() {
        return this.ventas;
    }

    public void setVentas(Set<Venta> ventas) {
        if (this.ventas != null) {
            this.ventas.forEach(i -> i.removePersonalizacion(this));
        }
        if (ventas != null) {
            ventas.forEach(i -> i.addPersonalizacion(this));
        }
        this.ventas = ventas;
    }

    public Personalizacion ventas(Set<Venta> ventas) {
        this.setVentas(ventas);
        return this;
    }

    public Personalizacion addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.getPersonalizacions().add(this);
        return this;
    }

    public Personalizacion removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.getPersonalizacions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personalizacion)) {
            return false;
        }
        return getId() != null && getId().equals(((Personalizacion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Personalizacion{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", idCatedra=" + getIdCatedra() +
            "}";
    }
}
