package com.rdaniel.energyplatform.dtos.builders;

import com.rdaniel.energyplatform.dtos.DeviceDTO;
import com.rdaniel.energyplatform.dtos.DeviceDetailsDTO;
import com.rdaniel.energyplatform.entities.Device;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeviceBuilder {

    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(device.getId(), device.getDeviceDescription(), device.getLocationAddress(), device.getBaselineEnergyConsumption(), device.getUser());
    }

    public static DeviceDetailsDTO toDeviceDetailsDTO(Device device) {
        return new DeviceDetailsDTO(device.getId(), device.getDeviceDescription(), device.getSensorDescription(), device.getSensorMaxValue(), device.getLocationAddress(), device.getMaxEnergyConsumption(), device.getBaselineEnergyConsumption(), device.getUser());
    }

    public static Device toEntity(DeviceDetailsDTO deviceDetailsDTO) {
        return new Device(deviceDetailsDTO.getDeviceDescription(), deviceDetailsDTO.getSensorDescription(), deviceDetailsDTO.getSensorMaxValue(), deviceDetailsDTO.getLocationAddress(), deviceDetailsDTO.getMaxEnergyConsumption(), deviceDetailsDTO.getBaselineEnergyConsumption(), deviceDetailsDTO.getUser());
    }
}
