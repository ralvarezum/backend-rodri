package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.DispositivoAsserts.*;
import static com.mycompany.myapp.domain.DispositivoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DispositivoMapperTest {

    private DispositivoMapper dispositivoMapper;

    @BeforeEach
    void setUp() {
        dispositivoMapper = new DispositivoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDispositivoSample1();
        var actual = dispositivoMapper.toEntity(dispositivoMapper.toDto(expected));
        assertDispositivoAllPropertiesEquals(expected, actual);
    }
}
