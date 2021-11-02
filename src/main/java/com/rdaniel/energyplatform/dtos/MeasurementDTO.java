package com.rdaniel.energyplatform.dtos;

import com.rdaniel.energyplatform.entities.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO extends RepresentationModel<MeasurementDTO> {

    private UUID id;

    @EqualsAndHashCode.Include
    @NotNull
    private Double energyConsumption;

    @EqualsAndHashCode.Include
    @NotNull
    private Date timestamp;

    private Device device;
}
