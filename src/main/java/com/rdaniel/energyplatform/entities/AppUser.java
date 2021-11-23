package com.rdaniel.energyplatform.entities;

import com.rdaniel.energyplatform.entities.enums.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "app_user")
@ApiModel(description = "Class representing a user")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    //@ApiModelProperty(notes = "Unique id (UUID) for user", example = "45774962-e6f7-41f6-b940-72ef63fa1943", position = 0)
    private UUID id;

    @Column(name = "fullname", length = 127, nullable = false)
    @ApiModelProperty(notes = "Full name of the user", example = "John Smith", required = true, position = 1)
    private String fullName;

    @Column(name = "username", nullable = false)
    @ApiModelProperty(notes = "The username of the user", example = "john_smith", required = true, position = 2)
    private String username;

    @Column(name = "password")
    @ApiModelProperty(notes = "The password of the user", example = "john123", required = true, position = 3)
    private String password;

    @Column(name = "birthday")
    @ApiModelProperty(notes = "The birthday of the user", example = "1987-07-15", required = true, position = 4)
    private Date birthday;

    @Column(name = "address")
    @ApiModelProperty(notes = "The address of the user", example = "Cluj-Napoca", position = 5)
    private String address;

    @Column(name = "role")
    @ApiModelProperty(notes = "The user's role in the system", example = "Client", position = 6)
    private Role role;

    public AppUser(String fullName, String username, String password, Date birthday, String address, Role role) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.address = address;
        this.role = role;
    }
}
