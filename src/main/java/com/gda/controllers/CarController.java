package com.gda.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gda.domain.Cars;
import com.gda.dtos.Car;
import com.gda.persistence.DaoService;
import com.gda.utils.JsonFormatter;
import com.gda.utils.JsonResponseFactory;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;

public class CarController {
    public static Object getCarById(Request req, Response res) {
        Integer carId = Integer.valueOf(req.params("car_id"));

        Cars car = DaoService.INSTANCE.getCar(carId);
        if (car == null) {
            return JsonResponseFactory.createErrorResponse(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, String.format("The CarId (%d) was not found on the DB.", carId));
        }
        return JsonResponseFactory.createJsonResponse(res, HttpServletResponse.SC_OK, car);
    }

    public static Object deleteCarById(Request req, Response res) {
        Integer carId = Integer.valueOf(req.params("car_id"));

        DaoService.INSTANCE.deleteCar(carId);
        return JsonResponseFactory.createJsonResponse(res, HttpServletResponse.SC_OK, String.format("The CarId (%d) was removed from the DB.", carId));
    }

    public static Object createCar(Request req, Response res) {
        try {
            Car carJson = JsonFormatter.parse(req.body(), Car.class);

            Cars carDb = Cars.createFrom(carJson);

            DaoService.INSTANCE.merge(carDb);

            return JsonResponseFactory.createSuccessResponse(res, String.format("The CarID (%d) was created successfully.", carJson.getCarId()));
        } catch (Exception e) {
            return JsonResponseFactory.createErrorResponse(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "The Car could not be created. Reason: " + e.getMessage());
        }
    }

    public static Object updateCar(Request req, Response res) {
        try {
            Car carJson = JsonFormatter.parse(req.body(), Car.class);

            Cars carDb = DaoService.INSTANCE.getCar(carJson.getCarId());
            if (carDb == null) {
                return JsonResponseFactory.createErrorResponse(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, String.format("The CarId (%d) was not found on the DB.", carJson.getCarId()));
            }

            carDb = Cars.createFrom(carJson);

            DaoService.INSTANCE.merge(carDb);

            return JsonResponseFactory.createSuccessResponse(res, String.format("The CarID (%d) was updated successfully.", carJson.getCarId()));
        } catch (Exception e) {
            return JsonResponseFactory.createErrorResponse(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "The Car could not be updated. Reason: " + e.getMessage());
        }
    }
}
