package com.rdaniel.energyplatform.services;

import com.rdaniel.energyplatform.common.handlers.exceptions.model.ResourceNotFoundException;
import com.rdaniel.energyplatform.dtos.MeasurementDTO;
import com.rdaniel.energyplatform.dtos.builders.MeasurementBuilder;
import com.rdaniel.energyplatform.entities.Measurement;
import com.rdaniel.energyplatform.repositories.MeasurementRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;


    public List<MeasurementDTO> findMeasurements() {
        List<Measurement> measurements = measurementRepository.findAll();
        return measurements.stream()
                .map(MeasurementBuilder::toMeasurementDTO)
                .collect(Collectors.toList());
    }

    public MeasurementDTO findMeasurementById(UUID id) {
        Optional<Measurement> measurementOptional = measurementRepository.findById(id);
        if(measurementOptional.isEmpty()) {
            log.error("Measurement with id {} not found in DB.", id);
            throw new ResourceNotFoundException(Measurement.class.getSimpleName() + "with id: " + id);
        }
        return MeasurementBuilder.toMeasurementDTO(measurementOptional.get());
    }

    public MeasurementDTO findLatestMeasurement() {
        List<Measurement> measurements = measurementRepository.findAll();
        measurements.sort(Comparator.comparing(Measurement::getTimestamp).reversed());
        return MeasurementBuilder.toMeasurementDTO(measurements.get(0));
    }

    public UUID insert(MeasurementDTO measurementDTO) {
        Measurement measurement = MeasurementBuilder.toEntity(measurementDTO);
        measurement = measurementRepository.save(measurement);
        log.debug("Measurement with id {} was inserted in DB", measurement.getId());
        return measurement.getId();
    }

    public MeasurementDTO update(UUID id, MeasurementDTO measurementToUpdate) {
        Optional<Measurement> measurementOptional = measurementRepository.findById(id);
        if(measurementOptional.isEmpty()) {
            log.error("Measurement with id {} not found in DB.", id);
            throw new ResourceNotFoundException(Measurement.class.getSimpleName() + "with id: " + id);
        }
        Measurement measurementFromDB = measurementOptional.get();
        measurementFromDB.setEnergyConsumption(measurementToUpdate.getEnergyConsumption());
        measurementFromDB.setTimestamp(measurementToUpdate.getTimestamp());
        measurementFromDB.setDevice(measurementToUpdate.getDevice());
        measurementFromDB = measurementRepository.save(measurementFromDB);
        return MeasurementBuilder.toMeasurementDTO(measurementFromDB);
    }

    public void delete(UUID id) {
        Optional<Measurement> measurementOptional = measurementRepository.findById(id);
        if(measurementOptional.isEmpty()) {
            log.error("Measurement with id {} not found in DB.", id);
            throw new ResourceNotFoundException(Measurement.class.getSimpleName() + "with id: " + id);
        }
        measurementRepository.deleteById(id);
    }

    public List<MeasurementDTO> findMeasurementsByDeviceAndDate(UUID deviceId, Date givenDate) {
        List<Measurement> measurementsByDevice = measurementRepository.findMeasurementsByDevice_Id(deviceId);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        List<MeasurementDTO> measurementDTOS = new ArrayList<>();
        for(Measurement m : measurementsByDevice) {
            if(sdf.format(m.getTimestamp()).equals(sdf.format(givenDate))) {
                measurementDTOS.add(MeasurementBuilder.toMeasurementDTO(m));
            }
        }
        measurementDTOS.sort(Comparator.comparing(MeasurementDTO::getTimestamp));
        return measurementDTOS;
    }
}
