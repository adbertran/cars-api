package com.gda.config;

import com.gda.controllers.CarController;
import spark.Spark;
import spark.servlet.SparkApplication;

import static spark.Spark.*;

public class Router implements SparkApplication {
    @Override
    public void init() {

        delete("/car/delete", CarController::deleteCarById);


        Spark.notFound((req, res) -> {res.type("application/json");
            return "{\"message\":\"Invalid URL.\"}";});
    }
}
