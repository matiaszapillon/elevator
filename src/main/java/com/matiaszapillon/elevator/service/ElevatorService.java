package com.matiaszapillon.elevator.service;

import com.matiaszapillon.elevator.entity.Elevator;
import com.matiaszapillon.elevator.entity.ElevatorDTO;

public interface ElevatorService {
    Elevator findById(Long id);

    void updateElevator(Long elevatorId, ElevatorDTO elevatorDTO);

    void validateWeight(long weight, Elevator elevator);

    Elevator createNewElevator(Elevator elevator);
}
