package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Adicional;
import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.domain.Opcion;
import com.mycompany.myapp.domain.Personalizacion;
import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.repository.AdicionalRepository;
import com.mycompany.myapp.repository.DispositivoRepository;
import com.mycompany.myapp.repository.OpcionRepository;
import com.mycompany.myapp.repository.PersonalizacionRepository;
import com.mycompany.myapp.repository.VentaRepository;
import com.mycompany.myapp.service.VentaService;
import com.mycompany.myapp.service.dto.VentaDTO;
import com.mycompany.myapp.service.dto.VentaRequest;
import com.mycompany.myapp.service.mapper.VentaMapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Venta}.
 */
@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    private static final Logger log = LoggerFactory.getLogger(VentaServiceImpl.class);

    private final VentaRepository ventaRepository;
    private final DispositivoRepository dispositivoRepository;
    private final AdicionalRepository adicionalRepository;
    private final OpcionRepository opcionRepository;
    private final VentaMapper ventaMapper;
    private final WebClient webClient;

    private final String jwtToken =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2RyaWFsdmEiLCJleHAiOjE3NDEzNzk5MDMsImF1dGgiOiJST0xFX1VTRVIiLCJpYXQiOjE3MzI3Mzk5MDN9.T3pVYff40aEQSlf7MRP1p9giUEN3Nx19HMIwKI5gQ9DHGtsneMQg2QDMCAFIwObHvFGAeCcQFd4houIDejjRLQ";

    public VentaServiceImpl(
        VentaRepository ventaRepository,
        AdicionalRepository adicionalRepository,
        PersonalizacionRepository personalizacionRepository,
        VentaMapper ventaMapper,
        WebClient.Builder webClientBuilder,
        DispositivoRepository dispositivoRepository,
        OpcionRepository opcionRepository
    ) {
        this.ventaRepository = ventaRepository;
        this.dispositivoRepository = dispositivoRepository;
        this.adicionalRepository = adicionalRepository;
        this.opcionRepository = opcionRepository;
        this.ventaMapper = ventaMapper;
        this.webClient = webClientBuilder.baseUrl("http://192.168.194.254:8080/api/catedra").build();
    }

    @Override
    public VentaDTO save(VentaDTO ventaDTO) {
        log.debug("Request to save Venta : {}", ventaDTO);
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        return ventaMapper.toDto(venta);
    }

    @Override
    public VentaDTO update(VentaDTO ventaDTO) {
        log.debug("Request to update Venta : {}", ventaDTO);
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        return ventaMapper.toDto(venta);
    }

    @Override
    public Optional<VentaDTO> partialUpdate(VentaDTO ventaDTO) {
        log.debug("Request to partially update Venta : {}", ventaDTO);

        return ventaRepository
            .findById(ventaDTO.getId())
            .map(existingVenta -> {
                ventaMapper.partialUpdate(existingVenta, ventaDTO);
                return existingVenta;
            })
            .map(ventaRepository::save)
            .map(ventaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaDTO> findAll() {
        log.debug("Request to get all Ventas");
        return ventaRepository.findAll().stream().map(ventaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<VentaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ventaRepository.findAllWithEagerRelationships(pageable).map(ventaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VentaDTO> findOne(Long id) {
        log.debug("Request to get Venta : {}", id);
        return ventaRepository.findOneWithEagerRelationships(id).map(ventaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
    }

    @Transactional
    public Venta registrarVenta(VentaRequest request) {
        // 1. Buscar el dispositivo
        Dispositivo dispositivo = dispositivoRepository
            .findById(request.getIdDispositivo())
            .orElseThrow(() -> new IllegalArgumentException("Dispositivo no encontrado con ID: " + request.getIdDispositivo()));

        // 2. Crear la venta
        Venta venta = new Venta();
        venta.setDispositivo(dispositivo);
        venta.setFechaVenta(request.getFechaVenta() != null ? request.getFechaVenta() : ZonedDateTime.now());

        BigDecimal precioCalculado = dispositivo.getPrecioBase();

        // 3. Procesar personalizaciones a partir de las opciones enviadas
        if (request.getPersonalizaciones() != null) {
            for (VentaRequest.PersonalizacionRequest personalizacionRequest : request.getPersonalizaciones()) {
                // Buscar la opción por su ID
                Opcion opcion = opcionRepository
                    .findById(personalizacionRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Opción no encontrada con ID: " + personalizacionRequest.getId()));

                // Asociar la personalización explícitamente
                Personalizacion personalizacion = opcion.getPersonalizacion();
                if (personalizacion == null) {
                    throw new IllegalArgumentException(
                        "La opción con ID " + opcion.getId() + " no está asociada a ninguna personalización."
                    );
                }

                // Asocia solo la opción seleccionada a la personalización
                personalizacion.getOpcions().clear();
                personalizacion.addOpcion(opcion);

                // Asociar la personalización a la venta
                venta.addPersonalizacion(personalizacion);

                // Sumar el precio adicional de la opción seleccionada
                precioCalculado = precioCalculado.add(opcion.getPrecioAdicional());
            }
        }

        // 4. Procesar adicionales
        if (request.getAdicionales() != null) {
            for (VentaRequest.AdicionalRequest adicionalRequest : request.getAdicionales()) {
                Adicional adicional = adicionalRepository
                    .findById(adicionalRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Adicional no encontrado con ID: " + adicionalRequest.getId()));

                // Asociar el adicional a la venta
                venta.addAdicional(adicional);

                // Sumar el precio del adicional
                precioCalculado = precioCalculado.add(adicionalRequest.getPrecio());
            }
        }

        // 5. Guardar el precio final
        venta.setPrecioFinal(precioCalculado);

        // 6. Persistir la venta en la base de datos
        Venta ventaGuardada = ventaRepository.save(venta);

        // 7. Enviar el detalle de la venta al servicio externo
        registrarVentaEnServicioExterno(ventaGuardada);

        return ventaGuardada;
    }

    @Override
    public void registrarVentaEnServicioExterno(Venta venta) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idDispositivo", venta.getDispositivo().getId());

        // Construir el objeto de personalizaciones
        List<Map<String, Object>> personalizaciones = venta
            .getPersonalizacions()
            .stream()
            .map(personalizacion -> {
                // Tomar solo la opción seleccionada
                Opcion opcionSeleccionada = personalizacion
                    .getOpcions()
                    .stream()
                    .findFirst()
                    .orElseThrow(
                        () ->
                            new IllegalArgumentException(
                                "No se encontró una opción válida para la personalización: " + personalizacion.getId()
                            )
                    );

                // Retornar el mapeo correcto de personalización y opción
                return Map.<String, Object>of(
                    "id",
                    opcionSeleccionada.getId(), // ID de la opción seleccionada
                    "precio",
                    opcionSeleccionada.getPrecioAdicional() // Precio de la opción seleccionada
                );
            })
            .collect(Collectors.toList());
        requestBody.put("personalizaciones", personalizaciones);

        // Construir el objeto de adicionales
        List<Map<String, Object>> adicionales = venta
            .getAdicionals()
            .stream()
            .map(adicional ->
                Map.<String, Object>of(
                    "id",
                    adicional.getId(), // ID del adicional
                    "precio",
                    adicional.getPrecio() // Precio del adicional
                ))
            .collect(Collectors.toList());
        requestBody.put("adicionales", adicionales);

        // Agregar el precio final y la fecha de venta
        requestBody.put("precioFinal", venta.getPrecioFinal());
        requestBody.put("fechaVenta", venta.getFechaVenta().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        // Enviar la solicitud al servicio externo
        webClient
            .post()
            .uri("/vender")
            .bodyValue(requestBody)
            .header("Authorization", "Bearer " + jwtToken)
            .retrieve()
            .bodyToMono(String.class)
            .doOnSuccess(response -> log.info("Venta registrada exitosamente en el servicio externo: {}", response))
            .doOnError(error -> log.error("Error al registrar la venta en el servicio externo: {}", error.getMessage()))
            .subscribe();
    }
}
