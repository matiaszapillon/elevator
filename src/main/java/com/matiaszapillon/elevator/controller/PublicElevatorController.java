package com.matiaszapillon.elevator.controller;


import com.matiaszapillon.elevator.entity.ElevatorDTO;
import com.matiaszapillon.elevator.entity.PublicElevator;
import com.matiaszapillon.elevator.service.PublicElevatorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/publicelevators")
public class PublicElevatorController {

    private final PublicElevatorService publicElevatorService;

    public PublicElevatorController(PublicElevatorService publicElevatorService) {
        this.publicElevatorService = publicElevatorService;
    }


    @GetMapping
    public ResponseEntity<?> findAll() {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateElevator(@PathVariable Long id,@Valid @RequestBody ElevatorDTO elevatorToMove) {
        publicElevatorService.updateElevator(id, elevatorToMove);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
