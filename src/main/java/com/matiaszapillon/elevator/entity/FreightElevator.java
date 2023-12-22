package com.matiaszapillon.elevator.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "freight_elevator")
public class FreightElevator extends Elevator {

    public FreightElevator() {
        super(3000L);
    }

}
