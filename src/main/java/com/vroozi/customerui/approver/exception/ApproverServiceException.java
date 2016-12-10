package com.vroozi.customerui.approver.exception;

import com.vroozi.customerui.exception.AdminUIException;

public class ApproverServiceException extends AdminUIException {

    public ApproverServiceException(){
        super();
    }

    public ApproverServiceException(Exception exp){
        super(exp);
    }
}
