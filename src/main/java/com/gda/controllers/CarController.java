package com.gda.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gda.domain.CarsDb;
import com.gda.dtos.CarJson;
import com.gda.persistence.DaoService;
import com.gda.utils.JsonFormatter;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;

public class CarController {

    // POST - Crear un car
    public static Object createCar(Request req, Response res) {
        try {
            CarJson car = JsonFormatter.parse(req.body(), CarJson.class);
            CarsDb reg = CarsDb.createFrom(car);
            DaoService.INSTANCE.createCar(reg);

            res.status(HttpServletResponse.SC_CREATED);
            return car.format();

        }  catch (Exception e) {
            return "Algo no funcionó. " + e.getMessage();
        }

    }

    // GET - Consulta, buscar y devolver un json con los detalles del auto.
    public static Object getCarById(Request req, Response res) throws JsonProcessingException {
        Integer carId = Integer.valueOf(req.params("car_id"));
        CarsDb car = DaoService.INSTANCE.getCar(carId);
        if (car==null){
            return "Car not found.";
        }
        res.header("Content-Type", "application/json");
        return JsonFormatter.format(car);
    }

    // PUT - Actualizar un car
    public static Object updateCar(Request req, Response res) {
        try {
            CarsDb car = JsonFormatter.parse(req.body(), CarsDb.class);
            CarJson reg = CarJson.createCar(car);
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

    // DELETE - Borrar un auto según id y devolver success o fail.
    public static Object deleteCarById(Request req, Response res) throws JsonProcessingException {
        Integer carId= Integer.valueOf(req.params("car_id"));
        DaoService.INSTANCE.deleteCar(carId);
        res.status(HttpServletResponse.SC_OK);

        return "Se borro."; //retorno los datos del auto que borre.
    }


}




/*
Creación de recursos
La URL estará “abierta” (el recurso todavía no existe y por tanto no tiene id)
El método debe ser POST
http://eventos.com/api/eventos/3/comentarios
Respuesta a la creación de recursos

Resultados posibles:
403 (Acceso prohibido)
400 (petición incorrecta, p.ej. falta un campo o su valor no es válido)
500 (Error del lado del servidor al intentar crear el recurso, p.ej. se ha caído la BD)
201 (Recurso creado correctamente)

¿Qué URL tiene el recurso recién creado?
La convención en REST es devolverla en la respuesta como valor de la cabecera HTTP Location


Actualización de recursos
Método PUT
Según la ortodoxia REST, actualizar significaría cambiar TODOS los datos
PATCH es un nuevo método estándar HTTP (2010) pensado para cambiar solo ciertos datos. Muchos frameworks de programación REST todavía no lo soportan
Resultados posibles
Errores ya vistos con POST
201 (Recurso creado, cuando le pasamos el id deseado al servidor)
200 (Recurso modificado correctamente)


Eliminar recursos
Método DELETE
Algunos resultados posibles:
200 OK
404 Not found
500 Server error
Tras ejecutar el DELETE con éxito, las siguientes peticiones GET a la URL del recurso deberían devolver 404


Arquitectura REST
Reglas de una arquitectura REST
Interfaz uniforme
Peticiones sin estado
Cacheable
Separación de cliente y servidor
Sistema de Capas
Código bajo demanda (opcional)
Interfaz Uniforme

La interfaz de basa en recursos (por ejemplo el recurso Empleado (Id, Nombre, Apellido, Puesto, Sueldo)
El servidor mandará los datos (vía html, json, xml...) pero lo que tenga en su interior (BBDD por ejemplo) para el cliente es transparente
La representación del recurso que le llega al cliente, será suficiente para poder cambiar/borrar el recurso:
Suponiendo que tenga permisos
Por eso en el recurso solicitado se suele enviar un parámetro Id */