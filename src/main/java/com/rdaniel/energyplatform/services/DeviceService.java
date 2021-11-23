package com.rdaniel.energyplatform.services;

import com.rdaniel.energyplatform.common.handlers.exceptions.model.ResourceNotFoundException;
import com.rdaniel.energyplatform.dtos.DeviceDetailsDTO;
import com.rdaniel.energyplatform.dtos.MeasurementDTO;
import com.rdaniel.energyplatform.dtos.builders.DeviceBuilder;
import com.rdaniel.energyplatform.dtos.builders.MeasurementBuilder;
import com.rdaniel.energyplatform.entities.Device;
import com.rdaniel.energyplatform.entities.Measurement;
import com.rdaniel.energyplatform.repositories.DeviceRepository;
import com.rdaniel.energyplatform.repositories.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final MeasurementRepository measurementRepository;

    public List<DeviceDetailsDTO> findDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
                .map(DeviceBuilder::toDeviceDetailsDTO)
                .collect(Collectors.toList());
    }

    public DeviceDetailsDTO findDeviceById(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if(deviceOptional.isEmpty()) {
            log.error("Device with id {} not found in DB.", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + "with id: " + id);
        }
        return DeviceBuilder.toDeviceDetailsDTO(deviceOptional.get());
    }

    public UUID insert(DeviceDetailsDTO deviceDetailsDTO) {
        Device device = DeviceBuilder.toEntity(deviceDetailsDTO);
        device = deviceRepository.save(device);
        log.debug("Device with id {} was inserted in DB", device.getId());
        return device.getId();
    }

    public DeviceDetailsDTO update(UUID id, DeviceDetailsDTO deviceToUpdate) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if(deviceOptional.isEmpty()) {
            log.error("Device with id {} not found in DB.", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + "with id: " + id);
        }
        Device deviceFromDB = deviceOptional.get();
        deviceFromDB.setDeviceDescription(deviceToUpdate.getDeviceDescription());
        deviceFromDB.setBaselineEnergyConsumption(deviceToUpdate.getBaselineEnergyConsumption());
        deviceFromDB.setLocationAddress(deviceToUpdate.getLocationAddress());
        deviceFromDB.setSensorDescription(deviceToUpdate.getSensorDescription());
        deviceFromDB.setMaxEnergyConsumption(deviceToUpdate.getMaxEnergyConsumption());
        deviceFromDB.setSensorMaxValue(deviceToUpdate.getSensorMaxValue());
        deviceFromDB.setUser(deviceToUpdate.getUser());
        deviceFromDB = deviceRepository.save(deviceFromDB);
        return DeviceBuilder.toDeviceDetailsDTO(deviceFromDB);
    }

    public void delete (UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if(deviceOptional.isEmpty()) {
            log.error("Device with id {} not found in DB.", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + "with id: " + id);
        }
        deviceRepository.deleteById(id);
    }

    public List<MeasurementDTO> findDeviceMeasurements(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if(deviceOptional.isEmpty()) {
            log.error("Device with id {} not found in DB.", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + "with id: " + id);
        }
        Device device = deviceOptional.get();

        List<Measurement> measurements = measurementRepository.findAll();
        List<Measurement> deviceMeasurements = new ArrayList<>();
        for(Measurement measurement: measurements) {
            if(measurement.getDevice().equals(device)) {
                deviceMeasurements.add(measurement);
            }
        }

        return deviceMeasurements.stream()
                .map(MeasurementBuilder::toMeasurementDTO)
                .collect(Collectors.toList());
    }
}
