package com.rdaniel.energyplatform.queuing;

import com.rdaniel.energyplatform.common.config.RabbitmqConfig;
import com.rdaniel.energyplatform.dtos.DeviceDetailsDTO;
import com.rdaniel.energyplatform.dtos.MeasurementDTO;
import com.rdaniel.energyplatform.services.DeviceService;
import com.rdaniel.energyplatform.services.MeasurementService;
import com.rdaniel.energyplatform.services.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class Receiver {

    private final DeviceService deviceService;
    private final MeasurementService measurementService;
    private final WebSocketService webSocketService;

    public Receiver(DeviceService deviceService, MeasurementService measurementService, WebSocketService webSocketService) {
        this.deviceService = deviceService;
        this.measurementService = measurementService;
        this.webSocketService = webSocketService;
    }

    @RabbitListener(queues = RabbitmqConfig.queueName)
    public void receiveMessage(MeasurementDTO measurementDTO) {
        System.out.println("Received <" + measurementDTO.getEnergyConsumption() + ">");
        // check peak condition
        DeviceDetailsDTO device = deviceService.findDeviceById(measurementDTO.getDevice().getId());
        MeasurementDTO latestMeasurement = measurementService.findLatestMeasurement(device.getId());
        double peak = computePeak(measurementDTO, latestMeasurement);
        System.out.println("Peak: " + peak + " | Device: " + device.getDeviceDescription() + " " + device.getSensorMaxValue());
        System.out.println();
        if(peak > device.getSensorMaxValue()) {
            String message = "ALERT!\nPeak " + String.format("%.2f",peak) + " > " + device.getSensorMaxValue() + " for user " + device.getUser().getUsername() + "'s " + device.getDeviceDescription() + " (" + device.getId().toString() + ")";
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
