package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Adicional.
 */
@Entity
@Table(name = "adicional")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Adicional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio", precision = 21, scale = 2)
    private BigDecimal precio;

    @Column(name = "precio_gratis", precision = 21, scale = 2)
    private BigDecimal precioGratis;

    @Column(name = "id_catedra")
    private Long idCatedra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "adicionals", "caracteristicas", "personalizacions" }, allowSetters = true)
    private Dispositivo dispositivo;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "adicionals")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dispositivo", "adicionals", "personalizacions" }, allowSetters = true)
    private Set<Venta> ventas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Adicional id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Adicional nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Adicional descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return this.precio;
    }

    public Adicional precio(BigDecimal precio) {
        this.setPrecio(precio);
        return this;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getPrecioGratis() {
        return this.precioGratis;
    }

    public Adicional precioGratis(BigDecimal precioGratis) {
        this.setPrecioGratis(precioGratis);
        return this;
    }

    public void setPrecioGratis(BigDecimal precioGratis) {
        this.precioGratis = precioGratis;
    }

    public Long getIdCatedra() {
        return this.idCatedra;
    }

    public Adicional idCatedra(Long idCatedra) {
        this.setIdCatedra(idCatedra);
        return this;
    }

    public void setIdCatedra(Long idCatedra) {
        this.idCatedra = idCatedra;
    }

    public Dispositivo getDispositivo() {
        return this.dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Adicional dispositivo(Dispositivo dispositivo) {
        this.setDispositivo(dispositivo);
        return this;
    }

    public Set<Venta> getVentas() {
        return this.ventas;
    }

    public void setVentas(Set<Venta> ventas) {
        if (this.ventas != null) {
            this.ventas.forEach(i -> i.removeAdicional(this));
        }
        if (ventas != null) {
            ventas.forEach(i -> i.addAdicional(this));
        }
        this.ventas = ventas;
    }

    public Adicional ventas(Set<Venta> ventas) {
        this.setVentas(ventas);
        return this;
    }

    public Adicional addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.getAdicionals().add(this);
        return this;
    }

    public Adicional removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.getAdicionals().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adicional)) {
            return false;
        }
        return getId() != null && getId().equals(((Adicional) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adicional{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precio=" + getPrecio() +
            ", precioGratis=" + getPrecioGratis() +
            ", idCatedra=" + getIdCatedra() +
            "}";
    }
}
