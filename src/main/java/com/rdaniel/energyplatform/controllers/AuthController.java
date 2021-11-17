package com.rdaniel.energyplatform.controllers;

import com.rdaniel.energyplatform.common.security.AppUserPrincipal;
import com.rdaniel.energyplatform.common.utility.JwtUtils;
import com.rdaniel.energyplatform.dtos.AppUserDetailsDTO;
import com.rdaniel.energyplatform.entities.AppUser;
import com.rdaniel.energyplatform.entities.enums.Role;
import com.rdaniel.energyplatform.entities.enums.converters.RoleConverter;
import com.rdaniel.energyplatform.payload.request.LoginRequest;
import com.rdaniel.energyplatform.payload.response.JwtResponse;
import com.rdaniel.energyplatform.repositories.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        AppUserPrincipal userPrincipal = (AppUserPrincipal) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        RoleConverter converter = new RoleConverter();

        return ResponseEntity.ok(new JwtResponse(jwt, userPrincipal.getId() ,userPrincipal.getUsername(), converter.convertToEntityAttribute(roles.get(0))));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AppUserDetailsDTO userDTO) {
        if(appUserRepository.existsAppUserByUsername(userDTO.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken");
        }

        // create a new user
        AppUser user = new AppUser(userDTO.getFullName(), userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getBirthday(), userDTO.getAddress(), Role.CLIENT);
        appUserRepository.save(user);
        return ResponseEntity.ok("Registered successfully!");
    }
}
