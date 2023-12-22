package com.matiaszapillon.elevator.service;

import com.matiaszapillon.elevator.entity.Elevator;
import com.matiaszapillon.elevator.entity.ElevatorDTO;
import com.matiaszapillon.elevator.entity.FreightElevator;
import com.matiaszapillon.elevator.repository.FreightElevatorRepository;
import com.matiaszapillon.elevator.util.exception.ExceededWeightLimitException;
import com.matiaszapillon.elevator.util.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreightElevatorService implements ElevatorService {
    private final FreightElevatorRepository freightElevatorRepository;

    public FreightElevatorService(FreightElevatorRepository freightElevatorRepository) {
        this.freightElevatorRepository = freightElevatorRepository;
    }

    @Override
    public List<FreightElevator> findAll() {
        return freightElevatorRepository.findAll();
    }
    @Override
    public FreightElevator findById(Long id) {
        return freightElevatorRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Elevator not found with id " + id));
    }

    @Override
    public void updateElevator(Long elevatorId, ElevatorDTO elevatorDTO) {
        FreightElevator elevatorToUpdate = this.findById(elevatorId);
        this.validateWeight(elevatorDTO.getWeight(), elevatorToUpdate);
        elevatorToUpdate.setCurrentFloor(elevatorDTO.getFloor());
        freightElevatorRepository.save(elevatorToUpdate);
    }

    @Override
    public void validateWeight(long weight, Elevator elevator) {
        if(!elevator.isValidWeight(weight)) {
            throw new ExceededWeightLimitException("Weight limit has been exceeded - Unable to proceed");
        }
    }

    @Override
    public Elevator createNewElevator(Elevator elevator) {
        return freightElevatorRepository.save((FreightElevator) elevator);
    }
}
