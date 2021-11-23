package com.rdaniel.energyplatform.repositories;

import com.rdaniel.energyplatform.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    Measurement findMeasurementById(UUID id);

    List<Measurement> findMeasurementsByDevice_Id(UUID device_id);
}
