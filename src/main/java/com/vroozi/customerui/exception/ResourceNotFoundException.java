package com.vroozi.customerui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User: SURYA MANDADAPU
 * Date: 3/28/13
 * Time: 2:29 PM
 */
@ResponseStatus( value = HttpStatus.NOT_FOUND )
public class ResourceNotFoundException extends RuntimeException{
}
