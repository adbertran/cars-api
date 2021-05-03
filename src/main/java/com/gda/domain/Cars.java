package com.gda.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "cars")
public class Cars {
    private Integer carId;
    private String brand;
    private String colour;
    private BigDecimal value;
    private String licensePlate;
    private transient Timestamp dateCreated;
    private transient Timestamp lastUpdated;

    @Id
    @Column(name = "car_id", nullable = false)
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    @Basic
    @Column(name = "brand", nullable = false, length = 50)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "colour", nullable = false, length = 50)
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Basic
    @Column(name = "value", nullable = false, precision = 20, scale = 2)
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Basic
    @Column(name = "licencePlate", nullable = false, length = 50)
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licencePlate) {
        this.licensePlate = licencePlate;
    }

    @Basic
    @Column(name = "date_created", nullable = false, updatable = false)
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "last_updated", nullable = false)
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @PrePersist
    protected void onCreate() {
        this.lastUpdated = this.dateCreated = new Timestamp(new Date().getTime());
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = new Timestamp(new Date().getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cars cars = (Cars) o;
        return carId.equals(cars.carId) && brand.equals(cars.brand) && colour.equals(cars.colour) && value.equals(cars.value) && licensePlate.equals(cars.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId);
    }
}