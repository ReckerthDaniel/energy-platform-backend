package com.rdaniel.energyplatform.dtos;

import com.rdaniel.energyplatform.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDetailsDTO {

    private UUID id;

    @NotNull
    @EqualsAndHashCode.Include
    private String fullName;

    @NotNull
    @EqualsAndHashCode.Include
    private String username;

    @NotNull
    @EqualsAndHashCode.Include
    private String password;

    @EqualsAndHashCode.Include
    private Date birthday;

    @EqualsAndHashCode.Include
    private String address;

    @NotNull
    @EqualsAndHashCode.Include
    private Role role;
}
