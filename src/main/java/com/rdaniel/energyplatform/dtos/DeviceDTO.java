package com.rdaniel.energyplatform.dtos;

import com.rdaniel.energyplatform.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO extends RepresentationModel<DeviceDTO> {

    private UUID id;

    @EqualsAndHashCode.Include
    private String deviceDescription;

    @EqualsAndHashCode.Include
    private String locationAddress;

    @EqualsAndHashCode.Include
    private Double baselineEnergyConsumption;

    @EqualsAndHashCode.Include
    private AppUser user;
}
