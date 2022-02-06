package com.rdaniel.energyplatform.services;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.rdaniel.energyplatform.dtos.HistoricalMeasurementDTO;
import com.rdaniel.energyplatform.entities.Measurement;
import com.rdaniel.energyplatform.repositories.MeasurementRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AutoJsonRpcServiceImpl
public class JsonRpcServiceImpl implements JsonRpcService {

    private final MeasurementRepository measurementRepository;

    public JsonRpcServiceImpl( MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    public List<HistoricalMeasurementDTO> historicalEC(int daysNo, UUID deviceId) {
        List<Measurement> measurements = measurementRepository.findMeasurementsByDevice_Id(deviceId);
        measurements.sort(Comparator.comparing(Measurement::getTimestamp).reversed());
        Queue<Measurement> measurementsQueue = new LinkedList<>(measurements);

        Date date = DateUtils.truncate(new Date(), Calendar.HOUR);
//        date = DateUtils.addHours(date, -1);

        List<HistoricalMeasurementDTO> result = new ArrayList<>();

        for(int d = daysNo*24; d >= 0; d--) {
            double average = 0.0;
            int count = 0;

            while(date.equals(DateUtils.truncate(measurementsQueue.peek().getTimestamp(), Calendar.HOUR))) {
                average += measurementsQueue.poll().getEnergyConsumption();
                count++;
            }

            if(count != 0) {
                average /= count;
            }

            result.add(new HistoricalMeasurementDTO(average, date));

            date = DateUtils.addHours(date, -1);

        }

        return result;
    }

    @Override
    public List<Double> baselineEC(UUID deviceId) {
        List<HistoricalMeasurementDTO> measurements = this.historicalEC(7, deviceId);

        List<Double> result = new ArrayList<>(Collections.nCopies(24, 0.0));

        for(HistoricalMeasurementDTO measurement: measurements) {
            int hour = DateUtils.toCalendar(measurement.getTimestamp()).get(Calendar.HOUR_OF_DAY);
            result.set(hour, result.get(hour) + measurement.getEnergyConsumption());
        }

       return result.stream().map(r -> r/7).collect(Collectors.toList());
    }
}
