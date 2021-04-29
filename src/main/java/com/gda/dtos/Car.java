package com.gda.dtos;

import com.gda.domain.Cars;

import java.math.BigDecimal;

public class Car {
    private Integer carId;
    private String brand;
    private String colour;
    private BigDecimal value;
    private String licensePlate;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public static Car createFrom(Cars cars){
        Car car = new Car();
        car.carId = cars.getCarId();
        car.brand = cars.getBrand();
        car.colour = cars.getColour();
        car.value = cars.getValue();
        car.licensePlate = cars.getLicensePlate();

        return car;
    }
}
