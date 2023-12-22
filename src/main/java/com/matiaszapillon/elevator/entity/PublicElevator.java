package com.matiaszapillon.elevator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "public_elevator")
public class PublicElevator extends Elevator {
    public static final Long WEIGHT_LIMIT = 1000L;
    public static final Integer[] RESTRICTED_FLOORS = {-1,50};
    private String keycode;
    public PublicElevator() {
        super(WEIGHT_LIMIT);
    }

    public String getKeycode() {
        return keycode;
    }

    public void setKeycode(String keycode) {
        this.keycode = keycode;
    }

}
