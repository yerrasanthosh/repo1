package com.vroozi.customerui.company.model;

import java.io.Serializable;


public class PurchaseDocumentSettings implements Serializable  {
  private PurchaseOrderDocumentSettings purchaseOrder;

  public PurchaseOrderDocumentSettings getPurchaseOrder() {
    return purchaseOrder;
  }

  public void setPurchaseOrder(PurchaseOrderDocumentSettings purchaseOrder) {
    this.purchaseOrder = purchaseOrder;
  }
  
}
