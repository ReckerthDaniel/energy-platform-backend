package com.rdaniel.energyplatform.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Class representing a device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    @Column(name = "id")
    @ApiModelProperty(notes = "Unique id (UUID) for user", example = "45774962-e6f7-41f6-b940-72ef63fa1943", position = 0)
    private UUID id;

    @Column(name = "device_description")
    @ApiModelProperty(notes = "Device's description", example = "Washing machine", position = 1)
    private String deviceDescription;

    @Column(name = "sensor_description")
    @ApiModelProperty(notes = "Sensor's description (attached on device)", example = "EC sensor", position = 2)
    private String sensorDescription;

    @Column(name = "sensor_max_value")
    @ApiModelProperty(notes = "Maximum value the sensor can capture (kWh)", example = "2.3", required = true, position = 3)
    private Double sensorMaxValue;

    @Column(name = "location")
    @ApiModelProperty(notes = "The location of the device", example = "Kitchen", position = 4)
    private String locationAddress;

    @Column(name = "maxEC")
    @ApiModelProperty(notes = "Maximum EC of the device (kWh)", example = "5.3", required = true, position = 5)
    private Double maxEnergyConsumption;

    @Column(name = "avgEC")
    @ApiModelProperty(notes = "Average EC of the device", example = "2.5", required = true, position = 6)
    private Double baselineEnergyConsumption;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    @ApiModelProperty(notes = "The user having this device", required = true, position = 7)
    private AppUser user;

    public Device(String deviceDescription, String sensorDescription, Double sensorMaxValue, String locationAddress, Double maxEnergyConsumption, Double baselineEnergyConsumption, AppUser user) {
        this.deviceDescription = deviceDescription;
        this.sensorDescription = sensorDescription;
        this.sensorMaxValue = sensorMaxValue;
        this.locationAddress = locationAddress;
        this.maxEnergyConsumption = maxEnergyConsumption;
        this.baselineEnergyConsumption = baselineEnergyConsumption;
        this.user = user;
    }
}
