package com.rdaniel.energyplatform.repositories;

import com.rdaniel.energyplatform.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    Measurement findMeasurementById(UUID id);

    @Query(value = "SELECT m FROM Measurement m WHERE cast(m.timestamp AS date) = cast(:date AS date)", nativeQuery = true)
    List<Measurement> findMeasurementsByTimestamp(@Param("date") Date date);
}
