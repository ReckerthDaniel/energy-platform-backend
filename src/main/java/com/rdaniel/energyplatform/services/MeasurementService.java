package com.rdaniel.energyplatform.services;

import com.rdaniel.energyplatform.common.handlers.exceptions.model.ResourceNotFoundException;
import com.rdaniel.energyplatform.dtos.MeasurementDTO;
import com.rdaniel.energyplatform.dtos.builders.MeasurementBuilder;
import com.rdaniel.energyplatform.entities.Measurement;
import com.rdaniel.energyplatform.repositories.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    public List<MeasurementDTO> findMeasurementsByTimestamp(Date givenDate) throws ParseException {
        Calendar calendar1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar1.setTime(givenDate);

        List<Measurement> measurements = measurementRepository.findAll();
        List<MeasurementDTO> measurementDTOS = new ArrayList<>();
        for(Measurement measurement: measurements) {
            calendar2.setTime(measurement.getTimestamp());
            if((calendar2.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)) && (calendar2.get(Calendar.DAY_OF_YEAR) == calendar1.get(Calendar.DAY_OF_YEAR))) {
                measurementDTOS.add(MeasurementBuilder.toMeasurementDTO(measurement));
            }
        }

        measurementDTOS.sort(Comparator.comparing(MeasurementDTO::getTimestamp));

        return measurementDTOS;
    }
}
