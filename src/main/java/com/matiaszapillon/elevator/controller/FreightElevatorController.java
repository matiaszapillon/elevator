package com.matiaszapillon.elevator.controller;

import com.matiaszapillon.elevator.entity.ElevatorDTO;
import com.matiaszapillon.elevator.service.FreightElevatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/freightelevators")
public class FreightElevatorController {

    private final FreightElevatorService freightElevatorService;
    public FreightElevatorController(FreightElevatorService freightElevatorService) {

        this.freightElevatorService = freightElevatorService;
    }

    @GetMapping
    public ResponseEntity<?> getFreightElevator() {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateElevator(@PathVariable Long id, @RequestBody ElevatorDTO elevatorToMove) {
        return null;
    }
}
