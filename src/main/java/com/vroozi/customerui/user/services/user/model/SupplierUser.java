package com.vroozi.customerui.user.services.user.model;

public class SupplierUser extends User{

	private String uniqueSupplierId;
    private String emailMessage;
    private boolean sendEmail;

	public String getUniqueSupplierId() {
		return uniqueSupplierId;
	}
	
	public void setUniqueSupplierId(String uniqueSupplierId) {
		this.uniqueSupplierId = uniqueSupplierId;
	}


    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }
}
