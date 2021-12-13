package com.rdaniel.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    private MeasurementDTO measurementDTO;

    private Device device;

    @Autowired
    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    @Value("${sensor_id}")
    private String device_id;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.measurementDTO = new MeasurementDTO();
        this.device = new Device();
    }

    @Override
    public void run(String... args) throws InterruptedException, IOException {
        device.setId(device_id);
        measurementDTO.setDevice(device);

        MessageProperties msgProperties = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_JSON).build();
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\Projects\\producer\\src\\main\\resources\\sensor.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                measurementDTO.setEnergyConsumption(Double.parseDouble(values[0]));
                measurementDTO.setTimestamp(new Date());


                System.out.println("Sending message...");

                Message message = jackson2JsonMessageConverter.toMessage(measurementDTO, msgProperties);

                rabbitTemplate.convertAndSend(ProducerApplication.topicExchangeName, ProducerApplication.routingKey, message);
                Thread.sleep(10000);
            }
        }
    }


}