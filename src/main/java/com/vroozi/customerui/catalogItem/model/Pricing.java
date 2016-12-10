package com.vroozi.customerui.catalogItem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: SURYA MANDADAPU
 * Date: 12/18/12
 * Time: 9:46 PM
 */
public class Pricing {
    public String listPrice;
    public String priceUnit;
    public boolean priceEditable;
    public List<TieredPrice> tieredPrices =null;

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public boolean isPriceEditable() {
        return priceEditable;
    }

    public void setPriceEditable(boolean priceEditable) {
        this.priceEditable = priceEditable;
    }

    public List<TieredPrice> getTieredPrices() {
        return tieredPrices;
    }

    public void setTieredPrices(List<TieredPrice> tieredPrices) {
        this.tieredPrices = tieredPrices;
    }
}
