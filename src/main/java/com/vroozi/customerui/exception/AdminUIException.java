package com.vroozi.customerui.exception;

public class AdminUIException extends RuntimeException{

    public AdminUIException(){
        super();
    }

    public AdminUIException(String massage){
        super(massage);
    }

    public AdminUIException(Exception exp){
        super(exp);
    }

    public AdminUIException(String message, Exception exp){
        super(message, exp);
    }
}
