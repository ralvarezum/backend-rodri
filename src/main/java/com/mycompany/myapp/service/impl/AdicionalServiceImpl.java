package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Adicional;
import com.mycompany.myapp.repository.AdicionalRepository;
import com.mycompany.myapp.service.AdicionalService;
import com.mycompany.myapp.service.dto.AdicionalDTO;
import com.mycompany.myapp.service.mapper.AdicionalMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Adicional}.
 */
@Service
@Transactional
public class AdicionalServiceImpl implements AdicionalService {

    private static final Logger log = LoggerFactory.getLogger(AdicionalServiceImpl.class);

    private final AdicionalRepository adicionalRepository;

    private final AdicionalMapper adicionalMapper;

    public AdicionalServiceImpl(AdicionalRepository adicionalRepository, AdicionalMapper adicionalMapper) {
        this.adicionalRepository = adicionalRepository;
        this.adicionalMapper = adicionalMapper;
    }

    @Override
    public AdicionalDTO save(AdicionalDTO adicionalDTO) {
        log.debug("Request to save Adicional : {}", adicionalDTO);
        Adicional adicional = adicionalMapper.toEntity(adicionalDTO);
        adicional = adicionalRepository.save(adicional);
        return adicionalMapper.toDto(adicional);
    }

    @Override
    public AdicionalDTO update(AdicionalDTO adicionalDTO) {
        log.debug("Request to update Adicional : {}", adicionalDTO);
        Adicional adicional = adicionalMapper.toEntity(adicionalDTO);
        adicional = adicionalRepository.save(adicional);
        return adicionalMapper.toDto(adicional);
    }

    @Override
    public Optional<AdicionalDTO> partialUpdate(AdicionalDTO adicionalDTO) {
        log.debug("Request to partially update Adicional : {}", adicionalDTO);

        return adicionalRepository
            .findById(adicionalDTO.getId())
            .map(existingAdicional -> {
                adicionalMapper.partialUpdate(existingAdicional, adicionalDTO);

                return existingAdicional;
            })
            .map(adicionalRepository::save)
            .map(adicionalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdicionalDTO> findAll() {
        log.debug("Request to get all Adicionals");
        return adicionalRepository.findAll().stream().map(adicionalMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdicionalDTO> findOne(Long id) {
        log.debug("Request to get Adicional : {}", id);
        return adicionalRepository.findById(id).map(adicionalMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adicional : {}", id);
        adicionalRepository.deleteById(id);
    }
}
