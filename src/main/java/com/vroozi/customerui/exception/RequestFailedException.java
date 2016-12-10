package com.vroozi.customerui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User: SURYA MANDADAPU
 * Date: 3/28/13
 * Time: 2:34 PM
 */
//SERVICE_UNAVAILABLE
@ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
public class RequestFailedException extends RuntimeException{
}
