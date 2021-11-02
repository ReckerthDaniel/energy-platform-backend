package com.rdaniel.energyplatform.controllers;

import com.rdaniel.energyplatform.common.config.SwaggerConfig;
import com.rdaniel.energyplatform.dtos.MeasurementDTO;
import com.rdaniel.energyplatform.entities.Measurement;
import com.rdaniel.energyplatform.services.MeasurementService;
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
@RequestMapping(value = "/api/measurement")
@RequiredArgsConstructor
@Api(tags = SwaggerConfig.MEASUREMENT_TAG)
public class MeasurementController {

    private final MeasurementService measurementService;

    @ApiOperation(httpMethod = "GET", value = "Return list of all measurements in the DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Measurement.class),
            @ApiResponse(code = 404, message = "Could not find any measurement."),
            @ApiResponse(code = 400, message = "Getting all measurements failed.")
    })
    @GetMapping()
    public ResponseEntity<List<MeasurementDTO>> getMeasurements() {
        List<MeasurementDTO> dtos = measurementService.findMeasurements();
        for(MeasurementDTO dto : dtos) {
            Link userLink = linkTo(methodOn(MeasurementController.class)
                    .getMeasurement(dto.getId())).withRel("measurementDetails");
            dto.add(userLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Return a specific measurement by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Measurement.class),
            @ApiResponse(code = 404, message = "Could not find any measurement with id: {id given as path variable}"),
            @ApiResponse(code = 400, message = "Getting measurement with given id failed.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<MeasurementDTO> getMeasurement(@PathVariable("id") UUID measurementId) {
        MeasurementDTO dto = measurementService.findMeasurementById(measurementId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "POST", value = "Insert a new measurement")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Measurement.class),
            @ApiResponse(code = 404, message = "The measurement is not valid."),
            @ApiResponse(code = 400, message = "Inserting a new measurement failed.")
    })
    @PostMapping("/save")
    public ResponseEntity<UUID> insertMeasurement(@Valid @RequestBody MeasurementDTO measurementDTO) {
        UUID measurementId = measurementService.insert(measurementDTO);
        return new ResponseEntity<>(measurementId, HttpStatus.CREATED);
    }

    @ApiOperation(httpMethod = "PUT", value = "Update a specific measurement by its id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Measurement.class),
            @ApiResponse(code = 404, message = "Could not find any measurement with id: {id given as path variable}"),
            @ApiResponse(code = 400, message = "Updating measurement with given id failed.")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<MeasurementDTO> updateDevice(@PathVariable("id") UUID measurementId, @Valid @RequestBody MeasurementDTO measurementDTO) {
        MeasurementDTO updated = measurementService.update(measurementId, measurementDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete a specific measurement by its id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Measurement.class),
            @ApiResponse(code = 404, message = "Could not find any measurement with id: {id given as path variable}"),
            @ApiResponse(code = 400, message = "Deleting measurement with given id failed.")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable("id") UUID measurementId) {
        measurementService.delete(measurementId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
