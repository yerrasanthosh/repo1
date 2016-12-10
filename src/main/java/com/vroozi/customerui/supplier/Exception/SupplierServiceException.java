package com.vroozi.customerui.supplier.Exception;

import com.vroozi.customerui.exception.AdminUIException;

public class SupplierServiceException extends AdminUIException {

    public SupplierServiceException(){
        super();
    }

    public SupplierServiceException(Exception exp){
        super(exp);
    }
}
