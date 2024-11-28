package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Caracteristica;
import com.mycompany.myapp.repository.CaracteristicaRepository;
import com.mycompany.myapp.service.CaracteristicaService;
import com.mycompany.myapp.service.dto.CaracteristicaDTO;
import com.mycompany.myapp.service.mapper.CaracteristicaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Caracteristica}.
 */
@Service
@Transactional
public class CaracteristicaServiceImpl implements CaracteristicaService {

    private static final Logger log = LoggerFactory.getLogger(CaracteristicaServiceImpl.class);

    private final CaracteristicaRepository caracteristicaRepository;

    private final CaracteristicaMapper caracteristicaMapper;

    public CaracteristicaServiceImpl(CaracteristicaRepository caracteristicaRepository, CaracteristicaMapper caracteristicaMapper) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.caracteristicaMapper = caracteristicaMapper;
    }

    @Override
    public CaracteristicaDTO save(CaracteristicaDTO caracteristicaDTO) {
        log.debug("Request to save Caracteristica : {}", caracteristicaDTO);
        Caracteristica caracteristica = caracteristicaMapper.toEntity(caracteristicaDTO);
        caracteristica = caracteristicaRepository.save(caracteristica);
        return caracteristicaMapper.toDto(caracteristica);
    }

    @Override
    public CaracteristicaDTO update(CaracteristicaDTO caracteristicaDTO) {
        log.debug("Request to update Caracteristica : {}", caracteristicaDTO);
        Caracteristica caracteristica = caracteristicaMapper.toEntity(caracteristicaDTO);
        caracteristica = caracteristicaRepository.save(caracteristica);
        return caracteristicaMapper.toDto(caracteristica);
    }

    @Override
    public Optional<CaracteristicaDTO> partialUpdate(CaracteristicaDTO caracteristicaDTO) {
        log.debug("Request to partially update Caracteristica : {}", caracteristicaDTO);

        return caracteristicaRepository
            .findById(caracteristicaDTO.getId())
            .map(existingCaracteristica -> {
                caracteristicaMapper.partialUpdate(existingCaracteristica, caracteristicaDTO);

                return existingCaracteristica;
            })
            .map(caracteristicaRepository::save)
            .map(caracteristicaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CaracteristicaDTO> findAll() {
        log.debug("Request to get all Caracteristicas");
        return caracteristicaRepository
            .findAll()
            .stream()
            .map(caracteristicaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CaracteristicaDTO> findOne(Long id) {
        log.debug("Request to get Caracteristica : {}", id);
        return caracteristicaRepository.findById(id).map(caracteristicaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Caracteristica : {}", id);
        caracteristicaRepository.deleteById(id);
    }
}
