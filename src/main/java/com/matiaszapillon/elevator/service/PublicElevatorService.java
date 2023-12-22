package com.matiaszapillon.elevator.service;

import com.matiaszapillon.elevator.entity.Elevator;
import com.matiaszapillon.elevator.entity.ElevatorDTO;
import com.matiaszapillon.elevator.entity.PublicElevator;
import com.matiaszapillon.elevator.repository.PublicElevatorRepository;
import com.matiaszapillon.elevator.util.exception.ExceededWeightLimitException;
import com.matiaszapillon.elevator.util.exception.IncorrectKeyCodeException;
import com.matiaszapillon.elevator.util.exception.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Service
public class PublicElevatorService implements ElevatorService {
    private final PublicElevatorRepository publicElevatorRepository;
    private final PasswordEncoder passwordEncoder;

    public PublicElevatorService(PublicElevatorRepository publicElevatorRepository, PasswordEncoder passwordEncoder) {
        this.publicElevatorRepository = publicElevatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void validateKeyCardForRestrictedFloors(String introducedKeycode, PublicElevator elevator) {
        String keycodeFromElevator = elevator.getKeycode();
        if(StringUtils.hasText(introducedKeycode)
                && !BCrypt.checkpw(introducedKeycode, keycodeFromElevator)) {
            throw new IncorrectKeyCodeException("The keycard was either not introduced or it is incorrect");
        }

    }

    public PublicElevator findById(Long id) {
        return publicElevatorRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Elevator not found with id " + id));
    }

    @Override
    public void updateElevator(Long elevatorId, ElevatorDTO elevatorDTO) {
        PublicElevator elevator = this.findById(elevatorId);
        Integer selectedFloor = elevatorDTO.getFloor();
        if(isRestrictedFloor(selectedFloor)) {
            this.validateKeyCardForRestrictedFloors(elevator.getKeycode(), elevator);
        }
        this.validateWeight(elevatorDTO.getWeight(), elevator);
        elevator.setCurrentFloor(elevatorDTO.getFloor());
        publicElevatorRepository.save(elevator);
    }

    private boolean isRestrictedFloor(Integer selectedFloor) {
        return Arrays.asList(PublicElevator.RESTRICTED_FLOORS).contains(selectedFloor);
    }

    public void validateWeight(long weight, Elevator elevator) {
        if(!elevator.isValidWeight(weight)) {
            throw new ExceededWeightLimitException("Weight limit has been exceeded - Unable to proceed");
        }
    }

    @Override
    public Elevator createNewElevator(Elevator elevator) {
        PublicElevator publicElevator = (PublicElevator) elevator;
        String encryptedKeyCode = passwordEncoder.encode(publicElevator.getKeycode());
        publicElevator.setKeycode(encryptedKeyCode);
        return publicElevatorRepository.save(publicElevator);
    }
}
