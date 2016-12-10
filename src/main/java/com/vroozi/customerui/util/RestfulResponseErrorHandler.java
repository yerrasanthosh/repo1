package com.vroozi.customerui.util;

import java.util.List;

import java.io.IOException;

import org.slf4j.Logger;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import com.vroozi.customerui.exception.AdminUIException;

public class RestfulResponseErrorHandler implements ResponseErrorHandler {

    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    private Logger logger = null;

    public RestfulResponseErrorHandler(Logger logger){
        this.logger = logger;
    }

    public void handleError(ClientHttpResponse response) throws IOException {
        List<String> errorMsgHeader = response.getHeaders().get("x-app-err-msg");
        List<String> errorKeyHeader = response.getHeaders().get("x-app-err-key");

        String errorMessage = "RestClient Exception";
        if (errorMsgHeader != null) {
            errorMessage = errorMsgHeader.get(0);
        }

        String errorKey = "0000";
        if (errorKeyHeader != null) {
            errorKey = errorKeyHeader.get(0);
        }

        throw new AdminUIException(errorKey + ":" + errorMessage);
    }

    public boolean hasError(ClientHttpResponse response) throws IOException {
        if (errorHandler.hasError(response)) {
            logger.error("Error response status code: " + response.getStatusCode());
            logger.error("Error response status text" + response.getStatusText());
            return true;
        }
        return false;
    }
}