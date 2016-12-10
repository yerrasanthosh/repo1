package com.vroozi.customerui.catalogItem.model;

/**
 * User: SURYA MANDADAPU
 * Date: 12/18/12
 * Time: 9:39 PM
 */
public class Fulfillment {
    private String leadTime;
    private String minimumOrderQuantity;
    private String orderMultiplyQuantity;
    private boolean inStock;
    private String quantityOnHand;

    public String getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(String leadTime) {
        this.leadTime = leadTime;
    }

    public String getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    public void setMinimumOrderQuantity(String minimumOrderQuantity) {
        this.minimumOrderQuantity = minimumOrderQuantity;
    }

    public String getOrderMultiplyQuantity() {
        return orderMultiplyQuantity;
    }

    public void setOrderMultiplyQuantity(String orderMultiplyQuantity) {
        this.orderMultiplyQuantity = orderMultiplyQuantity;
    }

    public boolean getInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(String quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }
}
