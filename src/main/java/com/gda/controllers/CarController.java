package com.gda.controllers;

import spark.Request;
import spark.Response;

public class CarController {
    public static Object getCarById(Request req, Response res) {
        //Todo: buscar y devolver un json con los detalles del auto.
        return "get";
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
