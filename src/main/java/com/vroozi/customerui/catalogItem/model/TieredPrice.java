package com.vroozi.customerui.catalogItem.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * User: SURYA MANDADAPU
 * Date: 12/18/12
 * Time: 9:47 PM
 */
public class TieredPrice implements Serializable {

    public String id;
    public double from;
    public double to;
    public String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getFrom() {
        return from;
    }


    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
