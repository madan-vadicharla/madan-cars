package com.example.madancars;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Car {

    @ApiModelProperty(hidden = true)
    private @Id @GeneratedValue Long id;
    private String make;
    private String model;
    private String colour;
    private Integer year;

    public Car() {}

    public Car(String make, String model, String colour, Integer year) {
        this.make = make;
        this.model = model;
        this.colour = colour;
        this.year = year;
    }

}
