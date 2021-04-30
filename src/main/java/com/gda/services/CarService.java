package com.gda.services;

import com.gda.domain.Cars;
import com.gda.persistence.DaoService;

public class CarService {
    public static Cars getCarById(Integer carId) throws RuntimeException {
        if (carId == null || carId < 0) {
            throw new RuntimeException(String.format("The CarId (%d) is invalid.", carId));
        }
        Cars car = DaoService.INSTANCE.getCar(carId);
        if (car == null) {
            throw new RuntimeException(String.format("The CarId (%d) was not found on the DB.", carId));
        }
        return car;
    }

    public static void deleteCarById(Integer carId) {
        if (carId == null || carId < 0) {
            throw new RuntimeException(String.format("The CarId (%d) is invalid.", carId));
        }
        DaoService.INSTANCE.deleteCar(carId);
    }

    public static void createCar(Cars carDb) {
        if (carDb.getCarId() == null || carDb.getCarId() < 0) {
            throw new RuntimeException(String.format("The CarId (%d) is invalid.", carDb.getCarId()));
        }
        DaoService.INSTANCE.merge(carDb);
    }

    public static void validateCarRecord(Integer carId) throws RuntimeException {
        if (carId == null || carId < 0) {
            throw new RuntimeException(String.format("The CarId (%d) is invalid.", carId));
        }
        Cars car = DaoService.INSTANCE.getCar(carId);
        if (car != null) {
            throw new RuntimeException(String.format("The CarID (%d) exists already.", carId));
        }
    }

    public static void updateCar(Cars car) {
        if (car.getCarId() == null || car.getCarId() < 0) {
            throw new RuntimeException(String.format("The CarId (%d) is invalid.", car.getCarId()));
        }
        DaoService.INSTANCE.merge(car);
    }
}
