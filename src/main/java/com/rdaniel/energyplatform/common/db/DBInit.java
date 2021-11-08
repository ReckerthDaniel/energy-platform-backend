package com.rdaniel.energyplatform.common.db;

import com.rdaniel.energyplatform.entities.AppUser;
import com.rdaniel.energyplatform.entities.enums.Role;
import com.rdaniel.energyplatform.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
public class DBInit implements CommandLineRunner {
    private AppUserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DBInit(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Delete all
        this.userRepository.deleteAll();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1999, Calendar.JULY, 4);
        AppUser daniel = new AppUser("Daniel Reckerth", "rdaniel", passwordEncoder.encode("daniel123"), calendar.getTime(), "Sibiu", Role.ADMIN);
        AppUser client1 = new AppUser("Client 1", "client1", passwordEncoder.encode("client1ABC"), calendar.getTime(), "Location 1", Role.CLIENT);

        List<AppUser> users = Arrays.asList(daniel, client1);
        // Save to db
        this.userRepository.saveAll(users);
    }
}
