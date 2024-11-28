package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Opcion;
import com.mycompany.myapp.repository.OpcionRepository;
import com.mycompany.myapp.service.OpcionService;
import com.mycompany.myapp.service.dto.OpcionDTO;
import com.mycompany.myapp.service.mapper.OpcionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Opcion}.
 */
@Service
@Transactional
public class OpcionServiceImpl implements OpcionService {

    private static final Logger log = LoggerFactory.getLogger(OpcionServiceImpl.class);

    private final OpcionRepository opcionRepository;

    private final OpcionMapper opcionMapper;

    public OpcionServiceImpl(OpcionRepository opcionRepository, OpcionMapper opcionMapper) {
        this.opcionRepository = opcionRepository;
        this.opcionMapper = opcionMapper;
    }

    @Override
    public OpcionDTO save(OpcionDTO opcionDTO) {
        log.debug("Request to save Opcion : {}", opcionDTO);
        Opcion opcion = opcionMapper.toEntity(opcionDTO);
        opcion = opcionRepository.save(opcion);
        return opcionMapper.toDto(opcion);
    }

    @Override
    public OpcionDTO update(OpcionDTO opcionDTO) {
        log.debug("Request to update Opcion : {}", opcionDTO);
        Opcion opcion = opcionMapper.toEntity(opcionDTO);
        opcion = opcionRepository.save(opcion);
        return opcionMapper.toDto(opcion);
    }

    @Override
    public Optional<OpcionDTO> partialUpdate(OpcionDTO opcionDTO) {
        log.debug("Request to partially update Opcion : {}", opcionDTO);

        return opcionRepository
            .findById(opcionDTO.getId())
            .map(existingOpcion -> {
                opcionMapper.partialUpdate(existingOpcion, opcionDTO);

                return existingOpcion;
            })
            .map(opcionRepository::save)
            .map(opcionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpcionDTO> findAll() {
        log.debug("Request to get all Opcions");
        return opcionRepository.findAll().stream().map(opcionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OpcionDTO> findOne(Long id) {
        log.debug("Request to get Opcion : {}", id);
        return opcionRepository.findById(id).map(opcionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Opcion : {}", id);
        opcionRepository.deleteById(id);
    }
}
