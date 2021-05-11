package com.gda.services;

import com.gda.domain.Cars;
import com.gda.exceptions.ApiException;
import com.gda.persistence.DaoService;

import javax.servlet.http.HttpServletResponse;

public class CarService {
    public static Cars getCarById(Integer carId) throws ApiException {
        Cars car = DaoService.INSTANCE.getCar(carId);
        if (car == null)
            throw new ApiException(String.format("The CarId (%d) was not found on the DB.", carId), HttpServletResponse.SC_NOT_FOUND);
        return car;
    }

    public static void deleteCarById(Integer carId) throws ApiException {
        CarService.validateRecordExists(carId);
        DaoService.INSTANCE.deleteCar(carId);
    }

    public static void createCar(Cars carDb) throws ApiException {
        CarService.validateDuplicateRecord(carDb.getCarId());
        DaoService.INSTANCE.merge(carDb);
    }

    public static void updateCar(Cars car) throws ApiException {
        CarService.validateRecordExists(car.getCarId());
        DaoService.INSTANCE.merge(car);
    }

    private static void validateDuplicateRecord(Integer carId) throws ApiException {
        Cars car = DaoService.INSTANCE.getCar(carId);
        if (car != null) throw new ApiException(String.format("The CarID (%d) already exists.", carId), HttpServletResponse.SC_CONFLICT);
    }

    private static void validateRecordExists(Integer carId) throws ApiException {
        CarService.getCarById(carId);
    }

}
