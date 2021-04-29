package com.gda.controllers;

import com.gda.dtos.CarJson;
import com.gda.persistence.DaoService;
import com.gda.utils.JsonFormatter;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;

public class CarController {
    public static Object getCarById(Request req, Response res) {
        //Todo: buscar y devolver un json con los detalles del auto.
        return "get";
    }

    public static Object deleteCarById(Request req, Response res) {
        //borrar un auto según id y devolver success o fail.

        try {
            CarJson carjson = JsonFormatter.parse(req.body(), CarJson.class);

            DaoService.INSTANCE.deleteCar(carjson.getCarId());

            res.header("Content-Type", "application/json");
            res.status(HttpServletResponse.SC_OK);
            return JsonFormatter.format(carjson);

        } catch (Exception e) {
            res.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Se rompio json: " + e.getMessage();
        }
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
