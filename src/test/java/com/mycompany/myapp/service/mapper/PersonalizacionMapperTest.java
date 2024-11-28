package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.PersonalizacionAsserts.*;
import static com.mycompany.myapp.domain.PersonalizacionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonalizacionMapperTest {

    private PersonalizacionMapper personalizacionMapper;

    @BeforeEach
    void setUp() {
        personalizacionMapper = new PersonalizacionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPersonalizacionSample1();
        var actual = personalizacionMapper.toEntity(personalizacionMapper.toDto(expected));
        assertPersonalizacionAllPropertiesEquals(expected, actual);
    }
}
