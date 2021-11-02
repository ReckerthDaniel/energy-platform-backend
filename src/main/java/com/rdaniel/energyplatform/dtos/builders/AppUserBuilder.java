package com.rdaniel.energyplatform.dtos.builders;

import com.rdaniel.energyplatform.dtos.AppUserDTO;
import com.rdaniel.energyplatform.dtos.AppUserDetailsDTO;
import com.rdaniel.energyplatform.entities.AppUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUserBuilder {

    public static AppUserDTO toAppUserDTO(AppUser appUser) {
        return new AppUserDTO(appUser.getId(), appUser.getFullName(), appUser.getUsername(), appUser.getRole());
    }

    public static AppUserDetailsDTO toAppUserDetailsDTO(AppUser appUser) {
        return new AppUserDetailsDTO(appUser.getId(), appUser.getFullName(), appUser.getUsername(), appUser.getPassword(), appUser.getBirthday(), appUser.getAddress(), appUser.getRole());
    }

    public static AppUser toEntity(AppUserDetailsDTO appUserDetailsDTO) {
        return new AppUser(appUserDetailsDTO.getFullName(), appUserDetailsDTO.getUsername(), appUserDetailsDTO.getPassword(), appUserDetailsDTO.getBirthday(), appUserDetailsDTO.getAddress(), appUserDetailsDTO.getRole());
    }
}
