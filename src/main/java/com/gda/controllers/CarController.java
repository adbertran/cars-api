package com.gda.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gda.domain.Cars;
import com.gda.dtos.CarJson;
import com.gda.persistence.DaoService;
import com.gda.utils.JsonFormatter;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;

public class CarController {
    //Get car by its ID.
    public static Object getCarById(Request req, Response res) {
        //Todo: Agregar manejo de errores.
        String carId =  req.params(":car_id");

        try {
            Cars car = DaoService.getDaoService().getCarById(Integer.valueOf(carId));

            res.header("Content-Type", "application/json");
            res.status(HttpServletResponse.SC_OK);

            //Verify if car exists.
            if (car != null)
                return JsonFormatter.format(CarJson.createForm(car));
            else
                return "Car " + carId + " not found.";

       } catch (NumberFormatException e) {
            res.status(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
            return "Invalid Car ID (" + carId + "). Car not found.";

        } catch (JsonProcessingException e) {
            res.status(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
            return "Invalid request. Car not found.";
        }

    }

    //Delete car by its ID.
    public static String deleteCarById(Request req, Response res) {
        //Todo: Agregar manejo de errores.
        String carId =  req.params(":car_id");

        try {
            //Get the car to validate if exists.
            Cars car = DaoService.getDaoService().getCarById(Integer.valueOf(carId));

            if (car != null) {
                DaoService.getDaoService().deleteCarById(Integer.valueOf(carId));

                res.header("Content-Type", "application/json");
                res.status(HttpServletResponse.SC_OK);

                return "Car (" + carId + ") " + car.getLicensePlate() + " successfully deleted.";

            } else
                return "Car " + carId + " could not be deleted. Car not found.";

        } catch (Exception e) {
            res.status(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();

            return "Car " + carId + " could not be deleted. Car not found.";
        }
    }

    //Update an existing car.
    public static Object updateCarById(Request req, Response res) {
        try {
            //Get request body and transform it into a CarJson object.
            CarJson carJson = JsonFormatter.parse(req.body(), CarJson.class);

            //Validate if car to be updated exists.
            Cars carToBeUpdated = DaoService.getDaoService().getCarById(Integer.valueOf(carJson.getCarId()));

            //Map the Json into the Car DB object.
            Cars car = Cars.createForm(carJson);

            System.out.println("DEBUG: ID to update: " + carJson.getCarId());

            if (carToBeUpdated != null) {
                DaoService.getDaoService().update(car);
                res.header("Content-Type", "application/json");
                res.status(HttpServletResponse.SC_OK);

                return JsonFormatter.format(carJson);

            } else
                return String.format("Car (%d) %s does not exists.%nCar could not be updated.", carJson.getCarId(), carJson.getLicensePlate());

        } catch (JsonProcessingException e) {
            res.header("Content-Type", "application/json");
            res.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();

            return String.format("Error %s: Car could not be updated.", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        }
    }

    //Create a new car.
    public static Object postCreateCar(Request req, Response res) {
        //Todo: crear un nuevo auto con los datos que vienen en el body del json y devolver success o fail.
        try {
            //Get request body and transform it into a CarJson object.
            CarJson carJson = JsonFormatter.parse(req.body(),CarJson.class);

            //Map the Json into the Car DB object.
            Cars car = Cars.createForm(carJson);

            //Verify that the car Id does not exists.
            Cars carExists = DaoService.getDaoService().getCarById(car.getCarId());

            if (carExists == null) {
                //Create car.
                DaoService.getDaoService().merge(car);
                res.header("Content-Type", "application/json");
                res.status(HttpServletResponse.SC_OK);

                return JsonFormatter.format(carJson);

            } else
                return "Car (" + String.valueOf(car.getCarId()) + " ) " + car.getLicensePlate() + " already exists. Car could not be created.";


        } catch (JsonProcessingException e) {
            res.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();

            return "Car could not be created.";
        }

    }
}
