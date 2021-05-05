package com.gda.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gda.domain.Cars;
import com.gda.dtos.CarJson;
import com.gda.exceptions.ApiException;
import com.gda.services.CarService;
import com.gda.utils.JsonFormatter;
import com.gda.utils.JsonResponseFactory;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CarController {
    public static String getCarById(Request req, Response res) throws ApiException {
        try{
            Integer carId = Integer.valueOf(req.params("car_id"));
            validateCarId(carId);
            Cars car = CarService.getCarById(carId);
            return JsonResponseFactory.createJsonResponse(res,
                    HttpServletResponse.SC_OK,
                    car);
        } catch (NumberFormatException e) {
            throw new ApiException(e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static Object deleteCarById(Request req, Response res) throws ApiException {
        try{
            Integer carId = Integer.valueOf(req.params("car_id"));
            validateCarId(carId);
            CarService.deleteCarById(carId);
            return JsonResponseFactory.createJsonResponse(res,
                    HttpServletResponse.SC_OK,
                    String.format("The CarId (%d) was removed from the DB.", carId));
        } catch (NumberFormatException e) {
            return JsonResponseFactory.createErrorResponse(res,
                    HttpServletResponse.SC_BAD_REQUEST,
                    e);
        }
    }

    public static Object createCar(Request req, Response res) throws ApiException {
        try {
            Cars carDb = Cars.createFrom(JsonFormatter.parse(req.body(), CarJson.class));
            validateCarId(carDb.getCarId());
            CarService.createCar(carDb);
            return JsonResponseFactory.createSuccessResponse(res,
                    carDb);
        } catch (JsonProcessingException e) {
            return JsonResponseFactory.createErrorResponse(res,
                    HttpServletResponse.SC_BAD_REQUEST,
                    e);
        }
    }

    public static Object updateCar(Request req, Response res) throws ApiException {
        try {
            Cars car = Cars.createFrom(JsonFormatter.parse(req.body(), CarJson.class));
            validateCarId(car.getCarId());
            CarService.updateCar(car);
            return JsonResponseFactory.createSuccessResponse(res,
                    car);
        } catch (JsonProcessingException e) {
            return JsonResponseFactory.createErrorResponse(res,
                    HttpServletResponse.SC_BAD_REQUEST,
                    e);
        }
    }

    private static void validateCarId(Integer carId) {
        if (carId == null || carId < 0) throw new RuntimeException(String.format("The CarId (%d) is invalid.", carId));
    }

}
