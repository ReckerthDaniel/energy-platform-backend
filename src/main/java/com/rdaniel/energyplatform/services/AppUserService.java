package com.rdaniel.energyplatform.services;

import com.rdaniel.energyplatform.common.handlers.exceptions.model.ResourceNotFoundException;
import com.rdaniel.energyplatform.dtos.AppUserDTO;
import com.rdaniel.energyplatform.dtos.AppUserDetailsDTO;
import com.rdaniel.energyplatform.dtos.builders.AppUserBuilder;
import com.rdaniel.energyplatform.entities.AppUser;
import com.rdaniel.energyplatform.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public List<AppUserDTO> findAppUsers() {
        List<AppUser> users = appUserRepository.findAll();
        return users.stream()
                .map(AppUserBuilder::toAppUserDTO)
                .collect(Collectors.toList());
    }

    public AppUserDetailsDTO findAppUserById(UUID id) {
        Optional<AppUser> userOptional = appUserRepository.findById(id);
        if(userOptional.isEmpty()) {
            log.error("User with id {} not found in DB.", id);
            throw new ResourceNotFoundException(AppUser.class.getSimpleName() + "with id: " + id);
        }
        return AppUserBuilder.toAppUserDetailsDTO(userOptional.get());
    }

    public AppUserDetailsDTO findAppUserByUsername(String username) {
        Optional<AppUser> userOptional = appUserRepository.findAppUserByUsername(username);
        if(userOptional.isEmpty()) {
            log.error("User with username {} not found in DB.", username);
            throw new ResourceNotFoundException(AppUser.class.getSimpleName() + "with username: " + username);
        }
        return AppUserBuilder.toAppUserDetailsDTO(userOptional.get());
    }

    public UUID insert(AppUserDetailsDTO appUserDetailsDTO) {
        AppUser user = AppUserBuilder.toEntity(appUserDetailsDTO);
        user = appUserRepository.save(user);
        log.debug("User with id {} was inserted in DB", user.getId());
        return user.getId();
    }

    public AppUserDetailsDTO update(UUID id, AppUserDetailsDTO userToUpdate) {
        Optional<AppUser> userOptional = appUserRepository.findById(id);
        if(userOptional.isEmpty()) {
            log.error("User with id {} not found in DB.", id);
            throw new ResourceNotFoundException(AppUser.class.getSimpleName() + "with id: " + id);
        }
        AppUser userFromDB = userOptional.get();
        userFromDB.setFullName(userToUpdate.getFullName());
        userFromDB.setAddress(userToUpdate.getAddress());
        userFromDB.setBirthday(userToUpdate.getBirthday());
        userFromDB.setUsername(userToUpdate.getUsername());
        userFromDB.setPassword(userToUpdate.getPassword());
        userFromDB.setUsername(userToUpdate.getUsername());
        userFromDB = appUserRepository.save(userFromDB);
        return AppUserBuilder.toAppUserDetailsDTO(userFromDB);
    }

    public void delete(UUID id) {
        Optional<AppUser> userOptional = appUserRepository.findById(id);
        if(userOptional.isEmpty()) {
            log.error("User with id {} not found in DB.", id);
            throw new ResourceNotFoundException(AppUser.class.getSimpleName() + "with id: " + id);
        }
        appUserRepository.deleteById(id);
    }
}
