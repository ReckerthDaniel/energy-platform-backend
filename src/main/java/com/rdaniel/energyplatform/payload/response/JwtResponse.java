package com.rdaniel.energyplatform.payload.response;

import com.rdaniel.energyplatform.entities.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class JwtResponse {

    private String accessToken;
    private final String tokenType = "Bearer";
    private UUID id;
    private String username;
    private Role role;

    public JwtResponse(String token, UUID id, String username, Role role) {
        this.accessToken = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
