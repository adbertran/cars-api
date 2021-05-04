package com.gda.services;

import com.gda.domain.Cars;
import com.gda.persistence.DaoService;

public class CarService {
    public static Cars getCarById(Integer carId) {
        CarService.validateCarId(carId);
        Cars car = DaoService.INSTANCE.getCar(carId);
        if (car == null) throw new RuntimeException(String.format("The CarId (%d) was not found on the DB.", carId));
        return car;
    }

    public static void deleteCarById(Integer carId) {
        CarService.validateCarId(carId);
        CarService.validateRecordExists(carId);
        DaoService.INSTANCE.deleteCar(carId);
    }

    public static void createCar(Cars carDb) {
        CarService.validateCarId(carDb.getCarId());
        CarService.validateDuplicateRecord(carDb.getCarId());
        DaoService.INSTANCE.merge(carDb);
    }

    public static void updateCar(Cars car) {
        CarService.validateCarId(car.getCarId());
        CarService.validateRecordExists(car.getCarId());
        DaoService.INSTANCE.merge(car);
    }

    private static void validateDuplicateRecord(Integer carId) {
        CarService.validateCarId(carId);
        Cars car = DaoService.INSTANCE.getCar(carId);
        if (car != null) throw new RuntimeException(String.format("The CarID (%d) exists already.", carId));
    }

    private static void validateRecordExists(Integer carId) {
        CarService.getCarById(carId);
    }

    private static void validateCarId(Integer carId) {
        if (carId == null || carId < 0) throw new RuntimeException(String.format("The CarId (%d) is invalid.", carId));
    }
}
