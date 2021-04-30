package com.gda.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gda.domain.Cars;
import com.gda.dtos.CarJson;
import com.gda.persistence.DaoService;
import com.gda.services.CarService;
import com.gda.utils.JsonFormatter;
import spark.Request;
import spark.Response;
import javax.servlet.http.HttpServletResponse;

public class CarController {
    //Get car by its ID.
    public static Object getCarById(Request req, Response res) {
        String carId =  req.params(":car_id");

        //Get the car by Id
        Object object = CarService.getCarService().getCarById(Integer.valueOf(carId));
        res.header("Content-Type", "application/json");

        //If we get a CarJson, car was found. All good.
        if (object instanceof CarJson) {
            res.status(HttpServletResponse.SC_OK);
        } else
            res.status(HttpServletResponse.SC_BAD_REQUEST);

        return object;
    }

    //Delete car by its ID.
    public static String deleteCarById(Request req, Response res) {
        String carId =  req.params(":car_id");
        String returnMsg = "";

        //Verify if delete was successfull or failed.
        if (CarService.getCarService().deleteCarById(Integer.valueOf(carId))) {
            res.status(HttpServletResponse.SC_OK);
            returnMsg = String.format("Car %s successfully deleted.", carId);

        } else {
            res.status(HttpServletResponse.SC_BAD_REQUEST);
            returnMsg = String.format("Car %s not found. The car was not deleted.", carId);
        }

        return returnMsg;
    }

    //Update an existing car.
    public static String updateCarById(Request req, Response res) {
        try {
            //Get request body and transform it into a CarJson object.
            CarJson carJson = JsonFormatter.parse(req.body(), CarJson.class);

            res.header("Content-Type", "application/json");
            if (CarService.getCarService().updateCarById(carJson)) {
                res.status(HttpServletResponse.SC_OK);

                return String.format("Car (%d) %s updated successfully.", carJson.getCarId(), carJson.getLicensePlate());

            } else {
                res.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                return String.format("Car (%d) %s does not exists.%nCar could not be updated.", carJson.getCarId(), carJson.getLicensePlate());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return String.format("Error %s: Car could not be updated.", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        }
    }

    //Create a new car.
    public static Object postCreateCar(Request req, Response res) {
        try {
            //Get request body and transform it into a CarJson object.
            CarJson carJson = JsonFormatter.parse(req.body(),CarJson.class);

            res.header("Content-Type", "application/json");
            if (CarService.getCarService().createCar(carJson)) {
                //Create car.
                res.status(HttpServletResponse.SC_OK);

                return JsonFormatter.format(carJson);

            } else
                return "Car already exists. Car could not be created.";


        } catch (JsonProcessingException e) {
            res.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();

            return String.format("Json Exception: Car could not be created.%n%s", e);
        }

    }
}
