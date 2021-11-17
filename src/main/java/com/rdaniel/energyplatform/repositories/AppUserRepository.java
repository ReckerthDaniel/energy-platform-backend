package com.rdaniel.energyplatform.repositories;

import com.rdaniel.energyplatform.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findAppUserByUsername(String username);

    Boolean existsAppUserByUsername(String username);
}
