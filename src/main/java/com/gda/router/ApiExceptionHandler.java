package com.gda.router;

import com.gda.exceptions.ApiException;
import com.gda.utils.JsonResponseFactory;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

public class ApiExceptionHandler<T extends Exception> implements ExceptionHandler<T> {
    @Override
    public void handle(T e, Request request, Response response) {
        ApiException apiException = e instanceof ApiException ? (ApiException) e : new ApiException("Unchecked Exception.", e);
        response.body(JsonResponseFactory.createErrorResponse(response, apiException.getHttpStatusCode(), apiException));
    }
}
