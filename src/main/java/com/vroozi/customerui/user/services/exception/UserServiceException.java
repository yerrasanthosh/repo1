package com.vroozi.customerui.user.services.exception;

import com.vroozi.customerui.exception.AdminUIException;

public class UserServiceException extends AdminUIException {

    public UserServiceException(){
        super();
    }

    public UserServiceException(Exception exp){
        super(exp);
    }
}
