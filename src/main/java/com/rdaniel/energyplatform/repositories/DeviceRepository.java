package com.rdaniel.energyplatform.repositories;

import com.rdaniel.energyplatform.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    Device findDeviceById(UUID id);

}
