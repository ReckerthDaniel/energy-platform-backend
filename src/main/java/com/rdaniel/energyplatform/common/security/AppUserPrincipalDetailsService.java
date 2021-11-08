package com.rdaniel.energyplatform.common.security;

import com.rdaniel.energyplatform.common.handlers.exceptions.model.ResourceNotFoundException;
import com.rdaniel.energyplatform.entities.AppUser;
import com.rdaniel.energyplatform.repositories.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AppUserPrincipalDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    public AppUserPrincipalDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> userOptional = this.userRepository.findAppUserByUsername(username);
        if(userOptional.isEmpty()) {
            log.error("User with username {} not found in DB.", username);
            throw new ResourceNotFoundException(AppUser.class.getSimpleName() + "with username: " + username);
        }

        return new AppUserPrincipal(userOptional.get());
    }
}
