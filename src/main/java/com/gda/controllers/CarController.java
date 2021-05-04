package com.gda.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gda.domain.Cars;
import com.gda.dtos.Car;
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
            return JsonResponseFactory.createJsonResponse(res,
                    HttpServletResponse.SC_OK,
                    car);
        } catch (NumberFormatException e) {
            return JsonResponseFactory.createErrorResponse(res,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "The CarID is invalid.");
        } catch (RuntimeException e) {
            return JsonResponseFactory.createErrorResponse(res,
                    HttpServletResponse.SC_NOT_FOUND,
                    e.getMessage());
        }
    }

    public static Object deleteCarById(Request req, Response res) {
        try{
            Integer carId = Integer.valueOf(req.params("car_id"));

            CarService.deleteCarById(carId);
            return JsonResponseFactory.createJsonResponse(res,
                    HttpServletResponse.SC_OK,
                    String.format("The CarId (%d) was removed from the DB.", carId));
        } catch (NumberFormatException e) {
            return JsonResponseFactory.createErrorResponse(res,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "The CarID is invalid.");
        } catch (RuntimeException e) {
            return JsonResponseFactory.createErrorResponse(res,
                    HttpServletResponse.SC_NOT_FOUND,
                    e.getMessage());
        }
    }

    public static Object createCar(Request req, Response res) {
        try {
            Cars carDb = Cars.createFrom(JsonFormatter.parse(req.body(), Car.class));

            CarService.createCar(carDb);
            return JsonResponseFactory.createSuccessResponse(res,
                    String.format("The CarID (%d) was created successfully.", carDb.getCarId()));
        } catch (JsonProcessingException e) {
            return JsonResponseFactory.createErrorResponse(res,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "The Car could not be created because the request contains invalid information.");
        } catch (Exception e) {
            return JsonResponseFactory.createErrorResponse(res,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "The Car could not be created. Reason: " + e.getMessage());
        }
    }

    public static Object updateCar(Request req, Response res) {
        try {
            Cars car = Cars.createFrom(JsonFormatter.parse(req.body(), Car.class));
            CarService.updateCar(car);
            return JsonResponseFactory.createSuccessResponse(res,
                    String.format("The CarID (%d) was updated successfully.", car.getCarId()));
        } catch (JsonProcessingException e) {
            return JsonResponseFactory.createErrorResponse(res,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "The Car could not be updated because the request contains invalid information.");
        }catch (Exception e) {
            return JsonResponseFactory.createErrorResponse(res,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "The Car could not be updated. Reason: " + e.getMessage());
        }
    }

}
