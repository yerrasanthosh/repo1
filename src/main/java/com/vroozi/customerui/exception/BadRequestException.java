package com.vroozi.customerui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User: SURYA MANDADAPU
 * Date: 3/28/13
 * Time: 2:27 PM
 */
@ResponseStatus( value = HttpStatus.BAD_REQUEST )
public class BadRequestException extends RuntimeException{
}
