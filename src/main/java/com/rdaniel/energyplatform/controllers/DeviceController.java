package com.rdaniel.energyplatform.controllers;


import com.rdaniel.energyplatform.common.config.SwaggerConfig;
import com.rdaniel.energyplatform.dtos.DeviceDTO;
import com.rdaniel.energyplatform.dtos.DeviceDetailsDTO;
import com.rdaniel.energyplatform.entities.Device;
import com.rdaniel.energyplatform.services.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/device")
@RequiredArgsConstructor
@Api(tags = SwaggerConfig.DEVICE_TAG)
public class DeviceController {

    private final DeviceService deviceService;

    @ApiOperation(httpMethod = "GET", value = "Return list of all devices in the DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Device.class),
            @ApiResponse(code = 404, message = "Could not find any device."),
            @ApiResponse(code = 400, message = "Getting all devices failed.")
    })
    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService.findDevices();
        for(DeviceDTO dto : dtos) {
            Link userLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(dto.getId())).withRel("deviceDetails");
            dto.add(userLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Return a specific device by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Device.class),
            @ApiResponse(code = 404, message = "Could not find any device with id: {id given as path variable}"),
            @ApiResponse(code = 400, message = "Getting device with given id failed.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDevice(@PathVariable("id") UUID deviceId) {
        DeviceDetailsDTO dto = deviceService.findDeviceById(deviceId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "POST", value = "Insert a new device")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Device.class),
            @ApiResponse(code = 404, message = "The device is not valid."),
            @ApiResponse(code = 400, message = "Inserting a new device failed.")
    })
    @PostMapping("/save")
    public ResponseEntity<UUID> insertDevice(@Valid @RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        UUID deviceId = deviceService.insert(deviceDetailsDTO);
        return new ResponseEntity<>(deviceId, HttpStatus.CREATED);
    }

    @ApiOperation(httpMethod = "PUT", value = "Update a specific device by its id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Device.class),
            @ApiResponse(code = 404, message = "Could not find any device with id: {id given as path variable}"),
            @ApiResponse(code = 400, message = "Updating device with given id failed.")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<DeviceDetailsDTO> updateDevice(@PathVariable("id") UUID deviceId, @Valid @RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        DeviceDetailsDTO updated = deviceService.update(deviceId, deviceDetailsDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete a specific device by its id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Device.class),
            @ApiResponse(code = 404, message = "Could not find any device with id: {id given as path variable}"),
            @ApiResponse(code = 400, message = "Deleting device with given id failed.")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable("id") UUID deviceId) {
        deviceService.delete(deviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
