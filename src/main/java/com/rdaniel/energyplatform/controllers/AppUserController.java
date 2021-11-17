package com.rdaniel.energyplatform.controllers;

import com.rdaniel.energyplatform.common.config.SwaggerConfig;
import com.rdaniel.energyplatform.dtos.AppUserDTO;
import com.rdaniel.energyplatform.dtos.AppUserDetailsDTO;
import com.rdaniel.energyplatform.dtos.DeviceDetailsDTO;
import com.rdaniel.energyplatform.entities.AppUser;
import com.rdaniel.energyplatform.services.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api")
@PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
@RequiredArgsConstructor
@Slf4j
@Api(tags = SwaggerConfig.APP_USER_TAG)
public class AppUserController {

    private final AppUserService appUserService;

    @ApiOperation(httpMethod = "GET", value = "Return list of all users in the DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = AppUser.class),
            @ApiResponse(code = 404, message = "Could not find any user."),
            @ApiResponse(code = 400, message = "Getting all users failed.")
    })
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppUserDetailsDTO>> getAppUsers() {
        List<AppUserDetailsDTO> dtos = appUserService.findAppUsers();
        for(AppUserDetailsDTO dto : dtos) {
            Link userLink = linkTo(methodOn(AppUserController.class)
                    .getAppUser(dto.getId())).withRel("appUserDetails");
            dto.add(userLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Return a specific user by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = AppUser.class),
            @ApiResponse(code = 404, message = "Could not find any user with id: {id given as path variable}"),
            @ApiResponse(code = 400, message = "Getting user with given id failed.")
    })
    @GetMapping(value = "/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppUserDetailsDTO> getAppUser(@PathVariable("id") UUID userId) {
        AppUserDetailsDTO dto = appUserService.findAppUserById(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "POST", value = "Insert a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = AppUser.class),
            @ApiResponse(code = 404, message = "The user is not valid."),
            @ApiResponse(code = 400, message = "Inserting a new user failed.")
    })
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UUID> insertAppUser(@Valid @RequestBody AppUserDetailsDTO appUserDetailsDTO) {
        UUID userId = appUserService.insert(appUserDetailsDTO);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @ApiOperation(httpMethod = "PUT", value = "Update a specific user by its id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = AppUser.class),
            @ApiResponse(code = 404, message = "Could not find any user with id: {id given as path variable}"),
            @ApiResponse(code = 400, message = "Updating user with given id failed.")
    })
    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppUserDetailsDTO> updateAppUser(@PathVariable("id") UUID userId, @Valid @RequestBody AppUserDetailsDTO appUserDetailsDTO) {
        AppUserDetailsDTO updated = appUserService.update(userId, appUserDetailsDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete a specific user by its id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = AppUser.class),
            @ApiResponse(code = 404, message = "Could not find any user with id: {id given as path variable}"),
            @ApiResponse(code = 400, message = "Deleting user with given id failed.")
    })
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAppUser(@PathVariable("id") UUID userId) {
        appUserService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/devices/{id}")
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    public ResponseEntity<List<DeviceDetailsDTO>> findAppUserDevices(@PathVariable("id") UUID userID) {
        List<DeviceDetailsDTO> dtos = appUserService.findAppUserDevices(userID);
        for(DeviceDetailsDTO dto : dtos) {
            Link userLink = linkTo(methodOn(AppUserController.class)
                    .getAppUser(dto.getId())).withRel("appUserDetails");
            dto.add(userLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
