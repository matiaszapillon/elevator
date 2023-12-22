package com.matiaszapillon.elevator.service;

import com.matiaszapillon.elevator.entity.Elevator;
import com.matiaszapillon.elevator.entity.ElevatorDTO;
import com.matiaszapillon.elevator.repository.FreightElevatorRepository;
import com.matiaszapillon.elevator.util.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FreightElevatorService implements ElevatorService {
    private final FreightElevatorRepository freightElevatorRepository;

    public FreightElevatorService(FreightElevatorRepository freightElevatorRepository) {
        this.freightElevatorRepository = freightElevatorRepository;
    }

    public Elevator findById(Long id) {
        return freightElevatorRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Elevator not found with id " + id));
    }

    @Override
    public void updateElevator(Long elevatorId, ElevatorDTO elevatorDTO) {

    }

    @Override
    public void validateWeight(long weight, Elevator elevator) {

    }

    @Override
    public Elevator createNewElevator(Elevator elevator) {
        return null;
    }
}
