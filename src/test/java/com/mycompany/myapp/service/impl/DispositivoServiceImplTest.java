package com.mycompany.myapp.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.mycompany.myapp.config.ExternalApiConfig;
import com.mycompany.myapp.domain.Adicional;
import com.mycompany.myapp.domain.Caracteristica;
import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.domain.Opcion;
import com.mycompany.myapp.domain.Personalizacion;
import com.mycompany.myapp.repository.DispositivoRepository;
import com.mycompany.myapp.service.dto.AdicionalDTO;
import com.mycompany.myapp.service.dto.CaracteristicaDTO;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import com.mycompany.myapp.service.dto.OpcionDTO;
import com.mycompany.myapp.service.dto.PersonalizacionDTO;
import com.mycompany.myapp.service.mapper.DispositivoMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

class DispositivoServiceImplTest {

    @Mock
    private DispositivoRepository dispositivoRepository;

    @Mock
    private DispositivoMapper dispositivoMapper;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private ExternalApiConfig externalApiConfig;

    private DispositivoServiceImpl dispositivoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        WebClient webClientMock = createMockedWebClient();

        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClientMock);

        when(externalApiConfig.getBaseUrl()).thenReturn("http://mock-api.com");
        when(externalApiConfig.getToken()).thenReturn("mock-token");

        dispositivoService = new DispositivoServiceImpl(dispositivoRepository, dispositivoMapper, webClientBuilder, externalApiConfig);
    }

    private WebClient createMockedWebClient() {
        WebClient webClientMock = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(webClientMock.get()).thenAnswer(invocation -> requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenAnswer(invocation -> requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenAnswer(invocation -> responseSpecMock);
        when(responseSpecMock.bodyToFlux(DispositivoDTO.class)).thenAnswer(invocation -> Flux.just(createSampleDispositivoDTO()));

        return webClientMock;
    }

    private DispositivoDTO createSampleDispositivoDTO() {
        DispositivoDTO dto = new DispositivoDTO();
        dto.setId(1L);
        dto.setNombre("Dispositivo");
        dto.setDescripcion("Descripcion");
        dto.setCaracteristicas(new ArrayList<>());
        dto.setPersonalizaciones(new ArrayList<>());
        dto.setAdicionales(new ArrayList<>());
        return dto;
    }

    private CaracteristicaDTO createCaracteristicaDTO() {
        CaracteristicaDTO dto = new CaracteristicaDTO();
        dto.setId(1L);
        dto.setNombre("Caracteristica 1");
        return dto;
    }

    private PersonalizacionDTO createPersonalizacionDTO() {
        PersonalizacionDTO dto = new PersonalizacionDTO();
        dto.setId(1L);
        dto.setNombre("Personalizacion 1");

        OpcionDTO opcionDTO = new OpcionDTO();
        opcionDTO.setId(1L);
        opcionDTO.setNombre("Opcion 1");
        opcionDTO.setPrecioAdicional(BigDecimal.valueOf(100));

        dto.setOpciones(List.of(opcionDTO));
        return dto;
    }

    private AdicionalDTO createAdicionalDTO() {
        AdicionalDTO dto = new AdicionalDTO();
        dto.setId(1L);
        dto.setNombre("Adicional 1");
        dto.setPrecio(BigDecimal.valueOf(50));
        return dto;
    }

    @Test
    void testFetchDispositivos_Success() {
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(1L);
        dispositivo.setNombre("Dispositivo");

        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setId(1L);
        caracteristica.setNombre("Caracteristica 1");
        dispositivo.addCaracteristica(caracteristica);

        Personalizacion personalizacion = new Personalizacion();
        personalizacion.setId(1L);
        personalizacion.setNombre("Personalizacion 1");

        Opcion opcion = new Opcion();
        opcion.setId(1L);
        opcion.setNombre("Opcion 1");
        opcion.setPrecioAdicional(BigDecimal.valueOf(100));
        personalizacion.addOpcion(opcion);

        dispositivo.addPersonalizacion(personalizacion);

        Adicional adicional = new Adicional();
        adicional.setId(1L);
        adicional.setNombre("Adicional 1");
        adicional.setPrecio(BigDecimal.valueOf(50));
        dispositivo.addAdicional(adicional);

        DispositivoDTO dispositivoDTO = createSampleDispositivoDTO();
        dispositivoDTO.setCaracteristicas(List.of(createCaracteristicaDTO()));
        dispositivoDTO.setPersonalizaciones(List.of(createPersonalizacionDTO()));
        dispositivoDTO.setAdicionales(List.of(createAdicionalDTO()));

        when(dispositivoMapper.toEntity(any(DispositivoDTO.class))).thenReturn(dispositivo);
        when(dispositivoMapper.toDto(any(Dispositivo.class))).thenReturn(dispositivoDTO);
        when(dispositivoRepository.save(any(Dispositivo.class))).thenReturn(dispositivo);
        when(dispositivoRepository.findByIdCatedra(any(Long.class))).thenReturn(Optional.empty());

        List<DispositivoDTO> result = dispositivoService.fetchDispositivos();

        assertNotNull(result);
        assertEquals(1, result.size());
        DispositivoDTO resultDTO = result.get(0);

        assertEquals("Dispositivo", resultDTO.getNombre());
        assertEquals(1, resultDTO.getCaracteristicas().size());
        assertEquals("Caracteristica 1", resultDTO.getCaracteristicas().get(0).getNombre());
        assertEquals(1, resultDTO.getPersonalizaciones().size());
        assertEquals("Personalizacion 1", resultDTO.getPersonalizaciones().get(0).getNombre());
        assertEquals(1, resultDTO.getPersonalizaciones().get(0).getOpciones().size());
        assertEquals("Opcion 1", resultDTO.getPersonalizaciones().get(0).getOpciones().get(0).getNombre());
        assertEquals(1, resultDTO.getAdicionales().size());
        assertEquals("Adicional 1", resultDTO.getAdicionales().get(0).getNombre());

        verify(dispositivoRepository, times(1)).save(any(Dispositivo.class));
    }

    @Test
    void testSaveDispositivo_Success() {
        DispositivoDTO dto = createSampleDispositivoDTO();
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(1L);

        when(dispositivoMapper.toEntity(dto)).thenReturn(dispositivo);
        when(dispositivoRepository.save(dispositivo)).thenReturn(dispositivo);
        when(dispositivoMapper.toDto(dispositivo)).thenReturn(dto);

        DispositivoDTO result = dispositivoService.save(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Dispositivo", result.getNombre());

        verify(dispositivoRepository, times(1)).save(dispositivo);
    }

    @Test
    void testFetchDispositivos_PrecioBaseNegativo() {
        DispositivoDTO dispositivoDTO = createSampleDispositivoDTO();
        dispositivoDTO.setCaracteristicas(List.of(createCaracteristicaDTO()));
        dispositivoDTO.setPrecioBase(BigDecimal.valueOf(-100));

        when(dispositivoMapper.toEntity(dispositivoDTO)).thenThrow(new IllegalArgumentException("Precio base no puede ser negativo"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dispositivoService.fetchDispositivos());

        assertEquals("Precio base no puede ser negativo", exception.getMessage());

        verify(dispositivoRepository, never()).save(any(Dispositivo.class));
    }

    @Test
    void testFetchDispositivos_CaracteristicasInvalidas() {
        DispositivoDTO dispositivoDTO = createSampleDispositivoDTO();
        dispositivoDTO.setCaracteristicas(null);

        when(dispositivoMapper.toEntity(dispositivoDTO)).thenThrow(new IllegalArgumentException("Caracteristicas inválidas"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dispositivoService.fetchDispositivos());

        assertEquals("Caracteristicas inválidas", exception.getMessage());

        verify(dispositivoRepository, never()).save(any(Dispositivo.class));
    }
}
