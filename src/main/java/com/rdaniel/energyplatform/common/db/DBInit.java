package com.rdaniel.energyplatform.common.db;

import com.rdaniel.energyplatform.entities.AppUser;
import com.rdaniel.energyplatform.entities.Device;
import com.rdaniel.energyplatform.entities.Measurement;
import com.rdaniel.energyplatform.entities.enums.Role;
import com.rdaniel.energyplatform.repositories.AppUserRepository;
import com.rdaniel.energyplatform.repositories.DeviceRepository;
import com.rdaniel.energyplatform.repositories.MeasurementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
public class DBInit implements CommandLineRunner {
    private AppUserRepository userRepository;
    private DeviceRepository deviceRepository;
    private MeasurementRepository measurementRepository;
    private PasswordEncoder passwordEncoder;

    public DBInit(AppUserRepository userRepository, DeviceRepository deviceRepository, MeasurementRepository measurementRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.measurementRepository = measurementRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws ParseException {
        // Delete all
        this.userRepository.deleteAll();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1999, Calendar.JULY, 4);
        AppUser daniel = new AppUser("Daniel Reckerth", "rdaniel", passwordEncoder.encode("daniel123"), calendar.getTime(), "Sibiu", Role.ADMIN);
        AppUser client1 = new AppUser("Client 1", "client1", passwordEncoder.encode("client1ABC"), calendar.getTime(), "Location 1", Role.CLIENT);

        Device device1 = new Device("Dishwasher", "Electrical", 27.53, "Bathroom", 20.0, 13.0, client1);
        List<Measurement> measurements = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            Double ec = new Random().nextDouble() * (27.53 - 1.3) + 1.3;
            DecimalFormat df = new DecimalFormat("##.##");
            String ecString = df.format(ec);
            Calendar c = Calendar.getInstance();
            int hours = new Random().nextInt(24);
            int mins = new Random().nextInt(60);
            int seconds = new Random().nextInt(60);
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, hours);
            c.set(Calendar.MINUTE, mins);
            c.set(Calendar.SECOND, seconds);
            measurements.add(new Measurement(Double.parseDouble(ecString), c.getTime(), device1));
        }

        List<AppUser> users = Arrays.asList(daniel, client1);
        // Save to db
        this.userRepository.saveAll(users);
        this.deviceRepository.save(device1);
        this.measurementRepository.saveAll(measurements);
    }
}
