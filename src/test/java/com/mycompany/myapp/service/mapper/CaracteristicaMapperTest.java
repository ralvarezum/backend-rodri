package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CaracteristicaAsserts.*;
import static com.mycompany.myapp.domain.CaracteristicaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CaracteristicaMapperTest {

    private CaracteristicaMapper caracteristicaMapper;

    @BeforeEach
    void setUp() {
        caracteristicaMapper = new CaracteristicaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCaracteristicaSample1();
        var actual = caracteristicaMapper.toEntity(caracteristicaMapper.toDto(expected));
        assertCaracteristicaAllPropertiesEquals(expected, actual);
    }
}
