package com.rdaniel.energyplatform.dtos;

import com.rdaniel.energyplatform.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDetailsDTO extends RepresentationModel<DeviceDetailsDTO> {

    private UUID id;

    private String deviceDescription;

    private String sensorDescription;

    @NotNull
    private Double sensorMaxValue;

    @NotNull
    private String locationAddress;

    @NotNull
    private Double maxEnergyConsumption;

    @NotNull
    private Double baselineEnergyConsumption;

    private AppUser user;
}
