package com.matiaszapillon.elevator.config;

import com.matiaszapillon.elevator.entity.Elevator;
import com.matiaszapillon.elevator.entity.FreightElevator;
import com.matiaszapillon.elevator.entity.PublicElevator;
import com.matiaszapillon.elevator.service.ElevatorService;
import com.matiaszapillon.elevator.service.FreightElevatorService;
import com.matiaszapillon.elevator.service.PublicElevatorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public CommandLineRunner loadData(List<ElevatorService> elevatorServiceList) {
        return (args) -> {
            PublicElevator publicElevator = new PublicElevator();
            publicElevator.setKeycode("12345");
            Elevator freightElevator = new FreightElevator();

            // initializes both elevators
            elevatorServiceList.forEach(
                    elevatorService -> {
                        if(elevatorService instanceof PublicElevatorService) {
                            elevatorService.createNewElevator(publicElevator);
                        }
                        if(elevatorService instanceof FreightElevatorService) {
                            elevatorService.createNewElevator(freightElevator);
                        }
                    }
            );
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        //I could create a SecurityConfig class with the @configuration annotation instead of creating the beans here also
        return new BCryptPasswordEncoder();
    }
}
