package com.gda.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    public static Object getCarById(Request req, Response res) throws JsonProcessingException {
        //Todo: buscar y devolver un json con los detalles del auto.
        String carId =  req.params(":car_id");

        Cars car = DaoService.getDaoService().getCar(Integer.valueOf(carId));

        res.header("Content-Type", "application/json");
        res.status(HttpServletResponse.SC_OK);

        if (car != null)
            return JsonFormatter.format(CarJson.createForm(car));
        else
            return "Car " + carId + " not found.";
    }

    public static Object deleteCarById(Request req, Response res) {
        //Todo: borrar un auto según id y devolver success o fail.
        return "delete";
    }

    public static Object createCar(Request req, Response res) {
        //Todo: crear un nuevo auto con los datos que vienen en el body del json y devolver success o fail.
        return "post";
    }

    public static Object updateCar(Request req, Response res) {
        //Todo: modificar un auto con los datos que vienen en el body del json y devolver success o fail.
        return "put";
    }
}
