package com.gda.dtos;

import com.gda.domain.Cars;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class Car {
    private Integer carId;
    private String brand;
    private String colour;
    private BigDecimal value;
    private String licensePlate;
    private String dateCreated;
    private String lastUpdated;


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

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    public String getLicensePlate() {
        return licensePlate;
    }

    public String getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    public static Car createCar(Cars car) {
        Car reg = new Car();
        reg.carId = car.getCarId();
        reg.brand = car.getBrand();
        reg.colour = car.getColour();
        reg.licensePlate = car.getLicensePlate();
        reg.value = car.getValue();
        reg.dateCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(car.getDateCreated());
        reg.lastUpdated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(car.getLastUpdated());
        return reg;
    }


}
