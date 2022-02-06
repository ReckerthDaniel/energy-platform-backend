package com.rdaniel.energyplatform.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class HistoricalMeasurementDTO {
    private Double energyConsumption;
    private Date timestamp;
}
