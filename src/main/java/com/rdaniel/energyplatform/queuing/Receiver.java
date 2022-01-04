package com.rdaniel.energyplatform.queuing;

import com.rdaniel.energyplatform.common.config.RabbitmqConfig;
import com.rdaniel.energyplatform.dtos.MeasurementDTO;
import com.rdaniel.energyplatform.entities.Device;
import com.rdaniel.energyplatform.repositories.DeviceRepository;
import com.rdaniel.energyplatform.services.MeasurementService;
import com.rdaniel.energyplatform.services.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class Receiver {

    private final DeviceRepository deviceRepository;
    private final MeasurementService measurementService;
    private final WebSocketService webSocketService;

    public Receiver(DeviceRepository deviceRepository, MeasurementService measurementService, WebSocketService webSocketService) {
        this.deviceRepository = deviceRepository;
        this.measurementService = measurementService;
        this.webSocketService = webSocketService;
    }

    @RabbitListener(queues = RabbitmqConfig.queueName)
    public void receiveMessage(MeasurementDTO measurementDTO) {
        System.out.println("Received <" + measurementDTO.getEnergyConsumption() + ">");

        // check peak condition
        Device device = deviceRepository.findDeviceById(measurementDTO.getDevice().getId());
        MeasurementDTO latestMeasurement = measurementService.findLatestMeasurement(device.getId());
        double peak = computePeak(measurementDTO, latestMeasurement);

        System.out.println("Peak: " + peak + " | Device: " + device.getDeviceDescription() + " " + device.getSensorMaxValue());
        System.out.println();

        if(peak > device.getSensorMaxValue()) {
            String message = "ALERT!\nPeak " + String.format("%.2f",peak) + " > " + device.getSensorMaxValue() + " for user " + device.getUser().getUsername() + "'s " + device.getDeviceDescription();
            webSocketService.sendMessage(device.getUser().getUsername(), message);
        }

        measurementService.insert(measurementDTO);
    }

    private double computePeak(MeasurementDTO receivedMeasurement, MeasurementDTO latestMeasurement) {
        double peak = receivedMeasurement.getEnergyConsumption() - latestMeasurement.getEnergyConsumption();
        peak /= ((receivedMeasurement.getTimestamp().getTime() - latestMeasurement.getTimestamp().getTime()) / (60.0 * 60 * 1000));
        return peak;
    }
}
