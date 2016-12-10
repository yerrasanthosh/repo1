package com.vroozi.customerui.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User: msundell
 * Date: 11/6/12
 * Time: 11:05 PM
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class HttpInternalServerError extends Exception {
    public HttpInternalServerError(String message) {
        super(message);
    }

    public HttpInternalServerError(Throwable cause) {
        super(cause);
    }

    public HttpInternalServerError(String message, Throwable cause) {
        super(message, cause);
    }
}