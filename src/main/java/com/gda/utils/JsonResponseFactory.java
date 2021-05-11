package com.gda.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gda.dtos.ErrorMessageJson;
import com.gda.exceptions.ApiException;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

public class JsonResponseFactory {
    public static String createErrorResponse(Response res, int statusCode, Throwable cause) {
        try{
            return createJsonResponse(res, statusCode, new ErrorMessageJson(cause.getMessage(), stackTraceToString(cause)));
        } catch (ApiException e) {
            res.header("Content-Type", "application/json");
            res.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "\"error\":\"Terrible error de Json.\"";
        }
    }

    public static String createSuccessResponse(Response res, Object o) throws ApiException {
        return createJsonResponse(res, HttpServletResponse.SC_OK, o);
    }

    public static String createJsonResponse(Response res, int statusCode, Object o) throws ApiException {
        try {
            res.header("Content-Type", "application/json");
            res.status(statusCode);
            return JsonFormatter.format(o);
        } catch (JsonProcessingException e) {
            throw new ApiException("JsonFormatter se pegó un palo.", e);
        }
    }

    private static String stackTraceToString(Throwable cause){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        cause.printStackTrace(pw);
        return sw.toString();
    }
}
