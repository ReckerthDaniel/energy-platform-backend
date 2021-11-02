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
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Class representing a measurement")
public class Measurement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    @ApiModelProperty(notes = "Unique id (UUID) for user", example = "45774962-e6f7-41f6-b940-72ef63fa1943", position = 0)
    private UUID id;

    @Column(name = "EC")
    @ApiModelProperty(notes = "EC at a given time ", example = "12.3", required = true, position = 1)
    private Double energyConsumption;

    @Column(name = "timestamp")
    @ApiModelProperty(notes = "The timestamp", example = "2021-11-01 15:30:25", required = true, position = 2)
    private Date timestamp;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "device_id")
    @ApiModelProperty(notes = "The device having this measurement", required = true, position = 3)
    private Device device;

    public Measurement(Double energyConsumption, Date timestamp, Device device) {
        this.energyConsumption = energyConsumption;
        this.timestamp = timestamp;
        this.device = device;
    }
}
