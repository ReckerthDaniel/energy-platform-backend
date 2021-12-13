package com.rdaniel.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeasurementDTO implements Serializable {

    @EqualsAndHashCode.Include
    @NotNull
    private Double energyConsumption;

    @EqualsAndHashCode.Include
    @NotNull
    private Date timestamp;

    private Device device;

}
