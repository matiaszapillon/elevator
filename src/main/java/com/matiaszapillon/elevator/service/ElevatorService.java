package com.matiaszapillon.elevator.service;

import com.matiaszapillon.elevator.entity.Elevator;
import com.matiaszapillon.elevator.entity.ElevatorDTO;

import java.util.List;

public interface ElevatorService {
    Elevator findById(Long id);

    List<? extends Elevator> findAll();

    void updateElevator(Long elevatorId, ElevatorDTO elevatorDTO);

    void validateWeight(long weight, Elevator elevator);

    Elevator createNewElevator(Elevator elevator);
}
