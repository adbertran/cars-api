package com.gda.dtos;

import com.gda.domain.Cars;

import java.math.BigDecimal;

public class CarJson extends JsonDto {
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

    public static CarJson createFrom(Cars cars){
        CarJson carJson = new CarJson();
        carJson.carId = cars.getCarId();
        carJson.brand = cars.getBrand();
        carJson.colour = cars.getColour();
        carJson.value = cars.getValue();
        carJson.licensePlate = cars.getLicensePlate();

        return carJson;
    }
}
