package com.mycompany.myapp.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    private VentaServiceImpl ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear el WebClient mock configurado
        WebClient webClientMock = createMockedWebClient();

        // Configurar el WebClient.Builder para devolver el WebClient mock
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClientMock);

        // Inicializar la instancia del servicio bajo prueba
        ventaService = new VentaServiceImpl(
            ventaRepository,
            adicionalRepository,
            personalizacionRepository,
            ventaMapper,
            webClientBuilder,
            dispositivoRepository,
            opcionRepository
        );
    }

    /**
     * Método auxiliar para configurar el flujo completo del WebClient mock.
     */
    private WebClient createMockedWebClient() {
        WebClient webClientMock = mock(WebClient.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpecMock = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpecMock = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec<?> requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        // Configurar el flujo del WebClient
        when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.uri(anyString())).thenReturn(requestBodySpecMock);
        when(requestBodySpecMock.bodyValue(any())).thenAnswer(invocation -> requestHeadersSpecMock);
        when(requestHeadersSpecMock.header(anyString(), anyString())).thenAnswer(invocation -> requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(String.class)).thenReturn(Mono.just("Mock Response"));

        return webClientMock;
    }

    @Test
    void testRegistrarVenta_Success() {
        // Configurar datos de entrada
        VentaRequest request = new VentaRequest();
        request.setIdDispositivo(1L);

        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(1L);
        dispositivo.setPrecioBase(BigDecimal.valueOf(100));

        // Configurar mocks
        when(dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Llamar al método bajo prueba
        Venta result = ventaService.registrarVenta(request);

        // Validar resultados
        assertNotNull(result);
        assertEquals(dispositivo, result.getDispositivo());
        assertEquals(BigDecimal.valueOf(100), result.getPrecioFinal());

        // Verificar interacciones
        verify(dispositivoRepository, times(1)).findById(1L);
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }

    @Test
    void testRegistrarVenta_DispositivoNoEncontrado() {
        // Configurar datos de entrada
        VentaRequest request = new VentaRequest();
        request.setIdDispositivo(99L);

        // Configurar mock para devolver vacío
        when(dispositivoRepository.findById(99L)).thenReturn(Optional.empty());

        // Llamar al método y esperar excepción
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> ventaService.registrarVenta(request),
            "Se esperaba una excepción IllegalArgumentException"
        );

        // Validar el mensaje de la excepción
        assertEquals("Dispositivo no encontrado con ID: 99", exception.getMessage());

        // Verificar que no se guardó ninguna venta
        verify(ventaRepository, never()).save(any());
    }

    @Test
    void testRegistrarVentaEnServicioExterno() {
        // Datos de entrada
        Venta venta = new Venta();
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(1L);
        venta.setDispositivo(dispositivo);
        venta.setPrecioFinal(BigDecimal.valueOf(150));
        venta.setFechaVenta(ZonedDateTime.now());

        // Llamar al método
        ventaService.registrarVentaEnServicioExterno(venta);

        // Verificar que se llamó al método URI con el argumento correcto
        verify(webClientBuilder.build().post(), times(1)).uri("/vender");
    }
}
