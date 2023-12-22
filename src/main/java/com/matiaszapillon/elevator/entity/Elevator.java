package com.matiaszapillon.elevator.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
//@DiscriminatorColumn(name = "elevator") check this approach if it's worth it
//it might be better to have two different tables for security concerns due to keycode of public elevators.
public abstract class Elevator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //represented by Kg
    private final Long weightLimit;
    private Integer currentFloor = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected Elevator(Long weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(Integer currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Long getWeightLimit() {
        return weightLimit;
    }

    public boolean isValidWeight(Long currentWeight) {
        return currentWeight == null || currentWeight < weightLimit;
    }
}
