package com.gda.controllers;

import com.gda.domain.Cars;
import com.gda.dtos.Car;
import com.gda.persistence.DaoService;
import com.gda.services.CarService;
import com.gda.utils.JsonFormatter;
import com.gda.utils.JsonResponseFactory;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;

public class CarController {
    public static Object getCarById(Request req, Response res) {
        try{
            Integer carId = Integer.valueOf(req.params("car_id"));
            Cars car = CarService.getCarById(carId);
            return JsonResponseFactory.createJsonResponse(res, HttpServletResponse.SC_OK, car);
        } catch (NumberFormatException e) {
            return JsonResponseFactory.createErrorResponse(res, HttpServletResponse.SC_BAD_REQUEST, "The CarID is invalid.");
        } catch (RuntimeException e) {
            return JsonResponseFactory.createErrorResponse(res, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    public static Object deleteCarById(Request req, Response res) {
        try{
            Integer carId = Integer.valueOf(req.params("car_id"));
            CarService.getCarById(carId);
            CarService.deleteCarById(carId);
            return JsonResponseFactory.createJsonResponse(res, HttpServletResponse.SC_OK, String.format("The CarId (%d) was removed from the DB.", carId));
        } catch (NumberFormatException e) {
            return JsonResponseFactory.createErrorResponse(res, HttpServletResponse.SC_BAD_REQUEST, "The CarID is invalid.");
        } catch (RuntimeException e) {
            return JsonResponseFactory.createErrorResponse(res, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    public static Object createCar(Request req, Response res) {
        try {
            Car carJson = JsonFormatter.parse(req.body(), Car.class);
            Cars carDb = Cars.createFrom(carJson);

            CarService.validateCarRecord(carDb.getCarId());
            CarService.createCar(carDb);
            return JsonResponseFactory.createSuccessResponse(res, String.format("The CarID (%d) was created successfully.", carJson.getCarId()));
        } catch (Exception e) {
            return JsonResponseFactory.createErrorResponse(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "The Car could not be created. Reason: " + e.getMessage());
        }
    }

    public static Object updateCar(Request req, Response res) {
        try {
            Car carJson = JsonFormatter.parse(req.body(), Car.class);
            Cars car = CarService.getCarById(carJson.getCarId());

            car = Cars.createFrom(carJson);
            CarService.updateCar(car);
            return JsonResponseFactory.createSuccessResponse(res, String.format("The CarID (%d) was updated successfully.", carJson.getCarId()));
        } catch (Exception e) {
            return JsonResponseFactory.createErrorResponse(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "The Car could not be updated. Reason: " + e.getMessage());
        }
    }

}
