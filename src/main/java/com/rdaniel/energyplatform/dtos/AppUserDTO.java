package com.rdaniel.energyplatform.dtos;

import com.rdaniel.energyplatform.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO extends RepresentationModel<AppUserDTO> {

    private UUID id;

    @EqualsAndHashCode.Include
    private String fullName;

    @EqualsAndHashCode.Include
    private String username;

    private Role role;
}
