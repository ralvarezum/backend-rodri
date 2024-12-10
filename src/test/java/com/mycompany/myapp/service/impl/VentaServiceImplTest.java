package com.mycompany.myapp.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.mycompany.myapp.config.ExternalApiConfig;
import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.repository.*;
import com.mycompany.myapp.service.dto.VentaRequest;
import com.mycompany.myapp.service.mapper.VentaMapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

class VentaServiceImplTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private DispositivoRepository dispositivoRepository;

    @Mock
    private PersonalizacionRepository personalizacionRepository;

    @Mock
    private OpcionRepository opcionRepository;

    @Mock
    private AdicionalRepository adicionalRepository;

    @Mock
    private VentaMapper ventaMapper;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private ExternalApiConfig externalApiConfig;

    private VentaServiceImpl ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        WebClient webClientMock = createMockedWebClient();

        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClientMock);

        when(externalApiConfig.getBaseUrl()).thenReturn("http://mock-api.com");
        when(externalApiConfig.getToken()).thenReturn("mock-token");

        ventaService = new VentaServiceImpl(
            ventaRepository,
            adicionalRepository,
            personalizacionRepository,
            ventaMapper,
            webClientBuilder,
            dispositivoRepository,
            opcionRepository,
            externalApiConfig
        );
    }

    private WebClient createMockedWebClient() {
        WebClient webClientMock = mock(WebClient.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpecMock = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpecMock = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec<?> requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.uri(anyString())).thenReturn(requestBodySpecMock);
        when(requestBodySpecMock.bodyValue(any())).thenAnswer(invocation -> requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(String.class)).thenReturn(Mono.just("Mock Response"));

        return webClientMock;
    }

    @Test
    void testRegistrarVenta_Success() {
        VentaRequest request = new VentaRequest();
        request.setIdDispositivo(1L);

        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(1L);
        dispositivo.setPrecioBase(BigDecimal.valueOf(100));

        when(dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Venta result = ventaService.registrarVenta(request);

        assertNotNull(result);
        assertEquals(dispositivo, result.getDispositivo());
        assertEquals(BigDecimal.valueOf(100), result.getPrecioFinal());

        verify(dispositivoRepository, times(1)).findById(1L);
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }

    @Test
    void testRegistrarVenta_DispositivoNoEncontrado() {
        VentaRequest request = new VentaRequest();
        request.setIdDispositivo(99L);

        when(dispositivoRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ventaService.registrarVenta(request));

        assertEquals("Dispositivo no encontrado con ID: 99", exception.getMessage());

        verify(ventaRepository, never()).save(any());
    }

    @Test
    void testRegistrarVentaEnServicioExterno() {
        Venta venta = new Venta();
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(1L);
        dispositivo.setIdCatedra(1L);
        venta.setDispositivo(dispositivo);
        venta.setPrecioFinal(BigDecimal.valueOf(150));
        venta.setFechaVenta(ZonedDateTime.now());

        ventaService.registrarVentaEnServicioExterno(venta);

        verify(webClientBuilder.build().post(), times(1)).uri("/vender");
    }

    @Test
    void testRegistrarVenta_PrecioFinalNegativo() {
        VentaRequest request = new VentaRequest();
        request.setIdDispositivo(1L);

        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(1L);
        dispositivo.setPrecioBase(BigDecimal.valueOf(-50));

        when(dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ventaService.registrarVenta(request));

        assertEquals("El precio final de la venta no puede ser negativo.", exception.getMessage());

        verify(ventaRepository, never()).save(any());
    }
}
