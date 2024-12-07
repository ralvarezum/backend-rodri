package com.mycompany.myapp.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.repository.DispositivoRepository;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import com.mycompany.myapp.service.mapper.DispositivoMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

class DispositivoServiceImplTest {

    private DispositivoServiceImpl dispositivoService;

    @Mock
    private DispositivoRepository dispositivoRepository;

    @Mock
    private DispositivoMapper dispositivoMapper;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WebClient.Builder webClientBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar el WebClient.Builder
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder); // Retorna el builder
        when(webClientBuilder.build()).thenReturn(webClient); // Devuelve el mock del WebClient

        // Configurar los mocks de WebClient con el tipo correcto
        when(webClient.get()).thenAnswer(invocation -> requestHeadersUriSpec); // Devuelve el mock de RequestHeadersUriSpec
        when(requestHeadersUriSpec.uri(anyString())).thenAnswer(invocation -> requestHeadersSpec); // Devuelve el mock de RequestHeadersSpec
        when(requestHeadersSpec.header(anyString(), anyString())).thenAnswer(invocation -> requestHeadersSpec); // Devuelve el mismo RequestHeadersSpec
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec); // Devuelve el mock de ResponseSpec
        when(responseSpec.bodyToFlux(DispositivoDTO.class)).thenReturn(Flux.just(createDispositivoDTO())); // Devuelve un Flux de DispositivoDTO

        // Inicializar el servicio bajo prueba
        dispositivoService = new DispositivoServiceImpl(dispositivoRepository, dispositivoMapper, webClientBuilder);
    }

    @Test
    void testFetchDispositivos() {
        // Arrange
        DispositivoDTO dispositivoDTO = createDispositivoDTO();

        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setNombre("Dispositivo 1");
        dispositivo.setPrecioBase(BigDecimal.valueOf(100));

        // Configura el mock del repositorio y mapper
        when(dispositivoRepository.findByNombre("Dispositivo 1")).thenReturn(Optional.empty());
        when(dispositivoMapper.toEntity(any(DispositivoDTO.class))).thenReturn(dispositivo);
        when(dispositivoMapper.toDto(any(Dispositivo.class))).thenReturn(dispositivoDTO);

        // Act
        List<DispositivoDTO> result = dispositivoService.fetchDispositivos();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombre()).isEqualTo("Dispositivo 1");
        assertThat(result.get(0).getPrecioBase()).isEqualTo(BigDecimal.valueOf(100));

        verify(dispositivoRepository, times(1)).findByNombre("Dispositivo 1");
        verify(dispositivoRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testFetchDispositivosPeriodicamente() {
        // Arrange
        DispositivoServiceImpl spyService = spy(dispositivoService);

        // Act
        spyService.fetchDispositivosPeriodicamente();

        // Assert
        verify(spyService, times(1)).fetchDispositivos();
    }

    // Helper method to create a mock DispositivoDTO
    private DispositivoDTO createDispositivoDTO() {
        DispositivoDTO dispositivoDTO = new DispositivoDTO();
        dispositivoDTO.setNombre("Dispositivo 1");
        dispositivoDTO.setPrecioBase(BigDecimal.valueOf(100));
        dispositivoDTO.setDescripcion("Descripción del dispositivo");
        dispositivoDTO.setIdCatedra(1L);
        return dispositivoDTO;
    }
}
