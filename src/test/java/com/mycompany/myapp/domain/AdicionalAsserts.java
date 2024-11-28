package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class AdicionalAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalAllPropertiesEquals(Adicional expected, Adicional actual) {
        assertAdicionalAutoGeneratedPropertiesEquals(expected, actual);
        assertAdicionalAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalAllUpdatablePropertiesEquals(Adicional expected, Adicional actual) {
        assertAdicionalUpdatableFieldsEquals(expected, actual);
        assertAdicionalUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalAutoGeneratedPropertiesEquals(Adicional expected, Adicional actual) {
        assertThat(expected)
            .as("Verify Adicional auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalUpdatableFieldsEquals(Adicional expected, Adicional actual) {
        assertThat(expected)
            .as("Verify Adicional relevant properties")
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()))
            .satisfies(e -> assertThat(e.getPrecio()).as("check precio").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getPrecio()))
            .satisfies(
                e ->
                    assertThat(e.getPrecioGratis())
                        .as("check precioGratis")
                        .usingComparator(bigDecimalCompareTo)
                        .isEqualTo(actual.getPrecioGratis())
            )
            .satisfies(e -> assertThat(e.getIdCatedra()).as("check idCatedra").isEqualTo(actual.getIdCatedra()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdicionalUpdatableRelationshipsEquals(Adicional expected, Adicional actual) {
        assertThat(expected)
            .as("Verify Adicional relationships")
            .satisfies(e -> assertThat(e.getDispositivo()).as("check dispositivo").isEqualTo(actual.getDispositivo()))
            .satisfies(e -> assertThat(e.getVentas()).as("check ventas").isEqualTo(actual.getVentas()));
    }
}
