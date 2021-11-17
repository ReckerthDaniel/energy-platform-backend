package com.rdaniel.energyplatform.dtos;

import com.rdaniel.energyplatform.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDetailsDTO extends RepresentationModel<AppUserDetailsDTO> {

    private UUID id;

    @NotBlank
    @Size(min = 3, max = 35)
    @EqualsAndHashCode.Include
    private String fullName;

    @NotBlank
    @Size(min = 3, max = 15)
    @EqualsAndHashCode.Include
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    @EqualsAndHashCode.Include
    private String password;

    @NotNull
    @EqualsAndHashCode.Include
    private Date birthday;

    @NotBlank
    @Size(min = 3, max = 40)
    @EqualsAndHashCode.Include
    private String address;

    @NotNull
    @EqualsAndHashCode.Include
    private Role role;

    public AppUserDetailsDTO(String fullName, String username, String password, Date birthday, String address, Role role) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.address = address;
        this.role = role;
    }
}
