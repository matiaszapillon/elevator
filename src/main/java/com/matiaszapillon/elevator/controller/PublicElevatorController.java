package com.matiaszapillon.elevator.controller;


import com.matiaszapillon.elevator.entity.ElevatorDTO;
import com.matiaszapillon.elevator.service.ElevatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/publicelevators")
public class PublicElevatorController {

    private final ElevatorService elevatorService;

    public PublicElevatorController(@Qualifier("publicElevatorService") ElevatorService elevatorService) {
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
