package com.vroozi.customerui.company.exception;

import com.vroozi.customerui.exception.AdminUIException;

public class CompanyServiceException extends AdminUIException {

	public CompanyServiceException(){
        super();
    }

    public CompanyServiceException(Exception exp){
        super(exp);
    }
}
