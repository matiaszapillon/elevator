package com.matiaszapillon.elevator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "freight_elevator")
public class FreightElevator extends Elevator {

    public static final Long WEIGHT_LIMIT = 3000L;

    public FreightElevator() {
        super(WEIGHT_LIMIT);
    }

}
