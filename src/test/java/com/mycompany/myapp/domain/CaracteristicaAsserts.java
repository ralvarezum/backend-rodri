package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CaracteristicaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaracteristicaAllPropertiesEquals(Caracteristica expected, Caracteristica actual) {
        assertCaracteristicaAutoGeneratedPropertiesEquals(expected, actual);
        assertCaracteristicaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaracteristicaAllUpdatablePropertiesEquals(Caracteristica expected, Caracteristica actual) {
        assertCaracteristicaUpdatableFieldsEquals(expected, actual);
        assertCaracteristicaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaracteristicaAutoGeneratedPropertiesEquals(Caracteristica expected, Caracteristica actual) {
        assertThat(expected)
            .as("Verify Caracteristica auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaracteristicaUpdatableFieldsEquals(Caracteristica expected, Caracteristica actual) {
        assertThat(expected)
            .as("Verify Caracteristica relevant properties")
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()))
            .satisfies(e -> assertThat(e.getIdCatedra()).as("check idCatedra").isEqualTo(actual.getIdCatedra()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaracteristicaUpdatableRelationshipsEquals(Caracteristica expected, Caracteristica actual) {
        assertThat(expected)
            .as("Verify Caracteristica relationships")
            .satisfies(e -> assertThat(e.getDispositivo()).as("check dispositivo").isEqualTo(actual.getDispositivo()));
    }
}
