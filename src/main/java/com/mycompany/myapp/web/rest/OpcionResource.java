package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.OpcionRepository;
import com.mycompany.myapp.service.OpcionService;
import com.mycompany.myapp.service.dto.OpcionDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Opcion}.
 */
@RestController
@RequestMapping("/api/opcions")
public class OpcionResource {

    private static final Logger log = LoggerFactory.getLogger(OpcionResource.class);

    private static final String ENTITY_NAME = "opcion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpcionService opcionService;

    private final OpcionRepository opcionRepository;

    public OpcionResource(OpcionService opcionService, OpcionRepository opcionRepository) {
        this.opcionService = opcionService;
        this.opcionRepository = opcionRepository;
    }

    /**
     * {@code POST  /opcions} : Create a new opcion.
     *
     * @param opcionDTO the opcionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opcionDTO, or with status {@code 400 (Bad Request)} if the opcion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OpcionDTO> createOpcion(@RequestBody OpcionDTO opcionDTO) throws URISyntaxException {
        log.debug("REST request to save Opcion : {}", opcionDTO);
        if (opcionDTO.getId() != null) {
            throw new BadRequestAlertException("A new opcion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        opcionDTO = opcionService.save(opcionDTO);
        return ResponseEntity.created(new URI("/api/opcions/" + opcionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, opcionDTO.getId().toString()))
            .body(opcionDTO);
    }

    /**
     * {@code PUT  /opcions/:id} : Updates an existing opcion.
     *
     * @param id the id of the opcionDTO to save.
     * @param opcionDTO the opcionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opcionDTO,
     * or with status {@code 400 (Bad Request)} if the opcionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opcionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OpcionDTO> updateOpcion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpcionDTO opcionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Opcion : {}, {}", id, opcionDTO);
        if (opcionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opcionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        opcionDTO = opcionService.update(opcionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opcionDTO.getId().toString()))
            .body(opcionDTO);
    }

    /**
     * {@code PATCH  /opcions/:id} : Partial updates given fields of an existing opcion, field will ignore if it is null
     *
     * @param id the id of the opcionDTO to save.
     * @param opcionDTO the opcionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opcionDTO,
     * or with status {@code 400 (Bad Request)} if the opcionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the opcionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the opcionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OpcionDTO> partialUpdateOpcion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpcionDTO opcionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Opcion partially : {}, {}", id, opcionDTO);
        if (opcionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opcionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OpcionDTO> result = opcionService.partialUpdate(opcionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opcionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /opcions} : get all the opcions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opcions in body.
     */
    @GetMapping("")
    public List<OpcionDTO> getAllOpcions() {
        log.debug("REST request to get all Opcions");
        return opcionService.findAll();
    }

    /**
     * {@code GET  /opcions/:id} : get the "id" opcion.
     *
     * @param id the id of the opcionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opcionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OpcionDTO> getOpcion(@PathVariable("id") Long id) {
        log.debug("REST request to get Opcion : {}", id);
        Optional<OpcionDTO> opcionDTO = opcionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opcionDTO);
    }

    /**
     * {@code DELETE  /opcions/:id} : delete the "id" opcion.
     *
     * @param id the id of the opcionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpcion(@PathVariable("id") Long id) {
        log.debug("REST request to delete Opcion : {}", id);
        opcionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
