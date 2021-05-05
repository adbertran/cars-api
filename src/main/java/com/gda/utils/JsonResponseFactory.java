package com.gda.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gda.dtos.ErrorMessageJson;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

public class JsonResponseFactory {
    public static String createErrorResponse(Response res, int statusCode, Throwable cause) {
        return createJsonResponse(res, statusCode, new ErrorMessageJson(cause.getMessage(), stackTraceToString(cause)));
    }

    public static String createSuccessResponse(Response res, Object o) {
        return createJsonResponse(res, HttpServletResponse.SC_OK, o);
    }

    public static String createJsonResponse(Response res, int statusCode, Object o) {
        try {
            res.header("Content-Type", "application/json");
            res.status(statusCode);
            return JsonFormatter.format(o);
        } catch (JsonProcessingException e) {
            res.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "description: \"JsonFormatter se pegó un palo: \"" + e.getMessage();
        }
    }

    private static String stackTraceToString(Throwable cause){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        cause.printStackTrace(pw);
        return sw.toString();
    }
}
