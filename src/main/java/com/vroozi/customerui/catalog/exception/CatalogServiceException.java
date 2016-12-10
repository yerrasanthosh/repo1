package com.vroozi.customerui.catalog.exception;

import com.vroozi.customerui.exception.AdminUIException;

public class CatalogServiceException extends AdminUIException {

    public CatalogServiceException(){
        super();
    }

    public CatalogServiceException(Exception exp){
        super(exp);
    }
}
