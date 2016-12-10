package com.vroozi.customerui.profile.exception;

import com.vroozi.customerui.exception.AdminUIException;

public class ProfileServiceException extends AdminUIException {

    public ProfileServiceException(){
        super();
    }

    public ProfileServiceException(Exception exp){
        super(exp);
    }
}
