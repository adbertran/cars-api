package com.gda.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gda.domain.Cars;
import com.gda.dtos.Car;
import com.gda.persistence.DaoService;
import com.gda.utils.JsonFormatter;
import javafx.scene.chart.ScatterChart;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;

public class CarController {

    // GET - Consulta, buscar y devolver un json con los detalles del auto.
    public static Object getCarById(Request req, Response res) throws JsonProcessingException {
        Integer carId = Integer.valueOf(req.params("car_id"));
        Cars car = DaoService.INSTANCE.getCar(carId);
        if (car==null){
            return "Car not found.";
        }
        res.header("Content-Type", "application/json");
        return JsonFormatter.format(car);
    }


    // DELETE - Borrar un auto según id y devolver success o fail.
    public static Object deleteCarById(Request req, Response res) throws JsonProcessingException {
        Integer carId= Integer.valueOf(req.params("car_id"));
        Cars car = DaoService.INSTANCE.deleteCar(carId);
        res.status(HttpServletResponse.SC_OK);
        if (car==null){
            res.status(HttpServletResponse.SC_BAD_REQUEST);
            return "Car not found.";
        }
        res.header("Content-Type", "application/json");
        return JsonFormatter.format(car); //retorno los datos del auto que borre.
    }

    //POST - Insertar un nuevo auto con los datos que vienen en el body del json y devolver success o fail.
    // no es idempotente por lo que no puede hacer lo mismo mas de una vez.
    public static Object createCar(Request req, Response res) {
    try {
        Cars car = JsonFormatter.parse(req.body(), Cars.class);
        Car reg = Car.createCar(car);
        DaoService.INSTANCE.createCar(reg);
        if (reg==null){
            res.status(HttpServletResponse.SC_BAD_REQUEST);
            return "Ya existe";
        }
        res.status(HttpServletResponse.SC_CREATED);
        return JsonFormatter.format(reg);

        }  catch (Exception e) {
            return "Algo no funcionó. " + e.getMessage();
        }

    }

    // actualizar car
    public static Object updateCar(Request req, Response res) {
        try {
            Cars car = JsonFormatter.parse(req.body(), Cars.class);
            Car reg = Car.createCar(car);
            DaoService.INSTANCE.merge(reg);
            if (reg==null){
                res.status(HttpServletResponse.SC_BAD_REQUEST);
                return "Car not found.";
            }
            res.status(HttpServletResponse.SC_OK);
            return JsonFormatter.format(reg);

        }  catch (Exception e) {
            return "Algo no funcionó. " + e.getMessage();
        }
    }
}
