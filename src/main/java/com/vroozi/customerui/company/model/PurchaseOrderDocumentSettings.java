package com.vroozi.customerui.company.model;

import java.io.Serializable;

public class PurchaseOrderDocumentSettings implements Serializable {
  private AllowedTermsAndConditions allowedTermsAndConditions;
  private String termsAndConditions;

  public AllowedTermsAndConditions getAllowedTermsAndConditions() {
    return allowedTermsAndConditions;
  }

  public void setAllowTermsAndConditions(AllowedTermsAndConditions allowedTermsAndConditions) {
    this.allowedTermsAndConditions = allowedTermsAndConditions;
  }

  public String getTermsAndConditions() {
    return termsAndConditions;
  }

  public void setTermsAndConditions(String termsAndConditions) {
    this.termsAndConditions = termsAndConditions;
  }

}
