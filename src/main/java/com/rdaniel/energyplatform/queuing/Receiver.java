package com.rdaniel.energyplatform.queuing;

import com.rdaniel.energyplatform.common.config.RabbitmqConfig;
import com.rdaniel.energyplatform.dtos.MeasurementDTO;
import com.rdaniel.energyplatform.entities.Device;
import com.rdaniel.energyplatform.repositories.DeviceRepository;
import com.rdaniel.energyplatform.services.MeasurementService;
import com.rdaniel.energyplatform.services.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
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
        MeasurementDTO latestMeasurement = measurementService.findLatestMeasurement();
        if(checkPeak(measurementDTO, latestMeasurement)) {
            webSocketService.sendMessage(latestMeasurement.getDevice().getUser().getUsername(), "Peak reached!");
        }
        measurementService.insert(measurementDTO);
    }

    private boolean checkPeak(MeasurementDTO receivedMeasurement, MeasurementDTO latestMeasurement) {
        Device device = deviceRepository.findDeviceById(receivedMeasurement.getDevice().getId());
        double peak = receivedMeasurement.getEnergyConsumption() - latestMeasurement.getEnergyConsumption();
        peak /= (receivedMeasurement.getTimestamp().getTime() - latestMeasurement.getTimestamp().getTime());
        peak *= (60 * 60 * 1000);
        return peak > device.getSensorMaxValue();
    }
}
