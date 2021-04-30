package com.gda.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gda.domain.Cars;
import com.gda.dtos.CarJson;
import com.gda.persistence.DaoService;
import com.gda.utils.JsonFormatter;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.servlet.http.HttpServletResponse;

public class CarService {
    //Singleton class ------------------------
    private CarService(){};
    private static CarService carService;

    public static CarService getCarService() {
        if (carService == null)
            carService = new CarService();

        return carService;
    }
    //End singleton ---------------------------

    //Get car by its ID.
    public Object getCarById(Integer carId) {
        try {
            Cars car = DaoService.getDaoService().getCarById(carId);

            //Verify if car exists.
            if (car != null)
                return JsonFormatter.format(CarJson.createForm(car));
            else
                return "Car " + carId + " not found.";

        } catch (NumberFormatException e) {
            e.printStackTrace();

            return "Invalid Car ID (" + carId + "). Car not found.";

        } catch (JsonProcessingException e) {
            e.printStackTrace();

            return String.format("Json exception: %s", e);
        }
    }

    public Boolean deleteCarById(Integer carId) {
        try {
            Cars car = DaoService.getDaoService().getCarById(carId);

            if (car != null)
                return DaoService.getDaoService().deleteCarById(carId);

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Updates a car by its ID.
    public Boolean updateCarById (CarJson carJson) {
        try {
            //Validate if car to be updated exists.
            Cars carExists = DaoService.getDaoService().getCarById(carJson.getCarId());

            System.out.println("DEBUG: ID to update: " + carJson.getCarId());

            if (carExists != null) {
                //Brand has more than 50 chars.
                if (carExists.getBrand().length() > 50)
                    return false;

                //Colour has more than 20 chars.
                if (carExists.getColour().length() > 20)
                    return false;

                //License plate has more than 7 chars.
                if (carExists.getLicensePlate().length() > 7)
                    return false;

                //Value must be a number."
                if (carExists.getValue().doubleValue() < 1)
                    return false;

                //Map the Json into the Car DB object.
                Cars car = Cars.createForm(carJson);
                return DaoService.getDaoService().update(car);

            } else
                return false;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    //Create a new car.
    public Boolean createCar (CarJson carJson) {
        try {
            //Verify that the car Id does not exists.
            Cars carExists = DaoService.getDaoService().getCarById(carJson.getCarId());

            if (carExists == null) {
                //Map the Json into the Car DB object.
                Cars car = Cars.createForm(carJson);
                //Create car.
                return DaoService.getDaoService().merge(car);
            } else
                return false;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }
}
