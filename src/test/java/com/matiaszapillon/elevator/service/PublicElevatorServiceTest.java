package com.matiaszapillon.elevator.service;

import com.matiaszapillon.elevator.config.SecurityConfig;
import com.matiaszapillon.elevator.entity.ElevatorDTO;
import com.matiaszapillon.elevator.entity.PublicElevator;
import com.matiaszapillon.elevator.repository.PublicElevatorRepository;
import com.matiaszapillon.elevator.util.exception.ExceededWeightLimitException;
import com.matiaszapillon.elevator.util.exception.IncorrectKeyCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@ContextConfiguration(classes = SecurityConfig.class) //Need to inject real PasswordEncoder bean to hash the keycode
@ExtendWith(SpringExtension.class)
class PublicElevatorServiceTest {

    private PublicElevatorRepository publicElevatorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private PublicElevatorService publicElevatorService;


    @BeforeEach
    void setUp() {
        this.publicElevatorRepository = Mockito.mock(PublicElevatorRepository.class);
        this.publicElevatorService = new PublicElevatorService(publicElevatorRepository, passwordEncoder);
    }

    @Test
    void givenAMovementToAUnrestrictedFloorWithoutKeyCard_whenUpdateElevatorIsCalled_thenShouldUpdateTheFloor() {
        // Arrange
        ElevatorDTO elevatorDTO = new ElevatorDTO();
        elevatorDTO.setFloor(10);
        elevatorDTO.setWeight(200L);

        PublicElevator expectedPublicElevator = new PublicElevator();
        expectedPublicElevator.setCurrentFloor(3);
        expectedPublicElevator.setId(1L);

        Mockito.when(publicElevatorRepository.findById(1L)).thenReturn(Optional.of(expectedPublicElevator));

        // Act
        publicElevatorService.updateElevator(1L, elevatorDTO);

        // Assert
        ArgumentCaptor<PublicElevator> publicElevatorArgumentCaptor = ArgumentCaptor.forClass(PublicElevator.class);

        Mockito.verify(publicElevatorRepository, times(1))
                .save(publicElevatorArgumentCaptor.capture());

        Assertions.assertEquals(10, publicElevatorArgumentCaptor.getValue().getCurrentFloor());
    }

    @Test
    void givenAMovementToARestrictedFloorWithKeyCard_whenUpdateElevatorIsCalled_thenShouldUpdateTheFloor() {
        // Arrange
        ElevatorDTO elevatorDTO = new ElevatorDTO();
        elevatorDTO.setFloor(50);
        elevatorDTO.setWeight(200L);
        elevatorDTO.setKeycode("12345");

        PublicElevator publicElevatorFromDatabase = new PublicElevator();
        publicElevatorFromDatabase.setCurrentFloor(3);
        publicElevatorFromDatabase.setId(1L);
        publicElevatorFromDatabase.setKeycode(passwordEncoder.encode("12345"));

        Mockito.when(publicElevatorRepository.findById(1L)).thenReturn(Optional.of(publicElevatorFromDatabase));

        // Act
        publicElevatorService.updateElevator(1L, elevatorDTO);

        // Assert
        ArgumentCaptor<PublicElevator> publicElevatorArgumentCaptor = ArgumentCaptor.forClass(PublicElevator.class);

        Mockito.verify(publicElevatorRepository, times(1))
                .save(publicElevatorArgumentCaptor.capture());

        Assertions.assertEquals(50, publicElevatorArgumentCaptor.getValue().getCurrentFloor());

    }

    @Test
    void givenAMovementToARestrictedFloorWithoutKeyCard_whenUpdateElevatorIsCalled_thenShouldThrowException() {
        // Arrange
        ElevatorDTO elevatorDTO = new ElevatorDTO();
        elevatorDTO.setFloor(50);
        elevatorDTO.setWeight(200L);

        PublicElevator publicElevatorFromDatabase = new PublicElevator();
        publicElevatorFromDatabase.setCurrentFloor(3);
        publicElevatorFromDatabase.setId(1L);
        publicElevatorFromDatabase.setKeycode(passwordEncoder.encode("12345"));

        Mockito.when(publicElevatorRepository.findById(1L)).thenReturn(Optional.of(publicElevatorFromDatabase));

        // Act
        IncorrectKeyCodeException incorrectKeyCodeException = assertThrows(IncorrectKeyCodeException.class,
                () -> publicElevatorService.updateElevator(1L, elevatorDTO));

        // Assert
        Assertions.assertEquals("The keycard was either not introduced or it is incorrect", incorrectKeyCodeException.getMessage());
        Mockito.verify(publicElevatorRepository, times(0)).save(publicElevatorFromDatabase);
    }

    @Test
    void givenAExceededWeight_whenUpdateElevatorIsCalled_thenShouldThrowException() {
        // Arrange
        ElevatorDTO elevatorDTO = new ElevatorDTO();
        elevatorDTO.setFloor(10);
        elevatorDTO.setWeight(1500L);

        PublicElevator publicElevatorFromDatabase = new PublicElevator();
        publicElevatorFromDatabase.setCurrentFloor(3);
        publicElevatorFromDatabase.setId(1L);

        Mockito.when(publicElevatorRepository.findById(1L)).thenReturn(Optional.of(publicElevatorFromDatabase));

        // Act
        ExceededWeightLimitException exceededWeightLimitException = assertThrows(ExceededWeightLimitException.class,
                () -> publicElevatorService.updateElevator(1L, elevatorDTO));

        // Assert
        Assertions.assertEquals("Weight limit has been exceeded - Unable to proceed", exceededWeightLimitException.getMessage());
        Mockito.verify(publicElevatorRepository, times(0)).save(publicElevatorFromDatabase);
    }

    @Test
    void whenCreateNewPublicElevator_shouldSetWeightLimitAndEncodeKeycode() {
        // Arrange
        PublicElevator publicElevator = new PublicElevator();
        publicElevator.setKeycode("12345");

        // Act
        publicElevatorService.createNewElevator(publicElevator);

        // Assert
        ArgumentCaptor<PublicElevator> publicElevatorArgumentCaptor = ArgumentCaptor.forClass(PublicElevator.class);

        Mockito.verify(publicElevatorRepository, times(1))
                .save(publicElevatorArgumentCaptor.capture());

        String encodedKeycode = publicElevatorArgumentCaptor.getValue().getKeycode();
        Assertions.assertNotNull(encodedKeycode);
        Assertions.assertNotEquals("12345", encodedKeycode);

        Long weightLimit = publicElevatorArgumentCaptor.getValue().getWeightLimit();
        Assertions.assertEquals(PublicElevator.WEIGHT_LIMIT, weightLimit);
    }
}