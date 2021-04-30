package com.gda.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gda.domain.Cars;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CarJson {
    private Integer carId;
    private String brand;
    private String colour;
    private BigDecimal value;
    private String licensePlate;
    private String dateCreated;
    private String lastUpdated;

    //CarId property
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    //Brand property
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    //Colour property
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    //Value property
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    //License Plate property
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    //Creation Date property
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    //Last update date property
    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    //Json methods ------------------------------------
    public static CarJson createForm(Cars car) {
        CarJson carJson = new CarJson();
        carJson.carId = car.getCarId();
        carJson.brand = car.getBrand();
        carJson.colour = car.getColour();
        carJson.licensePlate = car.getLicensePlate();
        carJson.value = car.getValue();
        carJson.dateCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(car.getDateCreated());
        carJson.lastUpdated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(car.getLastUpdated());

        return carJson;
    }
    //-------------------------------------------------
}
