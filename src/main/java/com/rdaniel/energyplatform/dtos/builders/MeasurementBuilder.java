package com.rdaniel.energyplatform.dtos.builders;

import com.rdaniel.energyplatform.dtos.MeasurementDTO;
import com.rdaniel.energyplatform.entities.Measurement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MeasurementBuilder {

    public static MeasurementDTO toMeasurementDTO(Measurement measurement) {
        return new MeasurementDTO(measurement.getId(), measurement.getEnergyConsumption(), measurement.getTimestamp(), measurement.getDevice());
    }

    public static Measurement toEntity(MeasurementDTO measurementDTO) {
        return new Measurement(measurementDTO.getEnergyConsumption(), measurementDTO.getTimestamp(), measurementDTO.getDevice());
    }
}
