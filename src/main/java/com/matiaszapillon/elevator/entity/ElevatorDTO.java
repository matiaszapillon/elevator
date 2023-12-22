package com.matiaszapillon.elevator.entity;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ElevatorDTO {
    @NotNull(message = "weight is mandatory.")
    private Long weight;
    private String keycode;

    @Min(value = -1, message = "Floor should not be less than -1")
    @Max(value = 50, message = "Floor should not be greater than 50")
    @NotNull(message = "floor is mandatory.")
    private Integer floor;

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getKeycode() {
        return keycode;
    }

    public void setKeycode(String keycode) {
        this.keycode = keycode;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}
