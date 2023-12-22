package com.matiaszapillon.elevator.controller;

import com.matiaszapillon.elevator.entity.ElevatorDTO;
import com.matiaszapillon.elevator.service.ElevatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/freightelevators")
public class FreightElevatorController {

    private final ElevatorService elevatorService;
    public FreightElevatorController(@Qualifier("freightElevatorService") ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(elevatorService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateElevator(@PathVariable Long id, @Valid @RequestBody ElevatorDTO elevatorToMove) {
        elevatorService.updateElevator(id, elevatorToMove);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
