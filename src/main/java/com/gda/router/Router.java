package com.gda.router;

import com.gda.controllers.CarController;
import spark.ExceptionHandler;
import spark.Spark;
import spark.servlet.SparkApplication;

import static spark.Spark.*;

public class Router implements SparkApplication {
    @Override
    public void init() {
        get("/cars/:car_id", CarController::getCarById);
        delete("/cars/:car_id", CarController::deleteCarById);
        put("/cars/:car_id", CarController::updateCar);
        post("/cars", CarController::createCar);

        Spark.notFound((req, res) -> {res.type("application/json");
            return "{\"message\":\"Invalid URL.\"}";});
        Spark.exception(Exception.class, new ApiExceptionHandler<>());
    }
}
