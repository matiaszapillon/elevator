package com.matiaszapillon.elevator.repository;

import com.matiaszapillon.elevator.entity.PublicElevator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicElevatorRepository extends JpaRepository<PublicElevator, Long> {
}
