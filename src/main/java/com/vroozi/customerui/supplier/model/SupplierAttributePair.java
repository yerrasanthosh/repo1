package com.vroozi.customerui.supplier.model;

/**
 * Provides ....
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 10/24/12:5:03 PM
 */
public class SupplierAttributePair {
    private String supplierId;
    private String attributeId;
    private String attributeName;
    private int boostValue;

    public SupplierAttributePair() {
        attributeId = "";
        supplierId = "";
        boostValue = 1;
    }

    public SupplierAttributePair(String attributeId, String supplierId, int boostValue, String attributeName) {
        this.attributeId = attributeId;
        this.supplierId = supplierId;
        this.boostValue = boostValue;
        this.attributeName = attributeName;
    }

    // PROPERTY: supplierId
    public String getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    // PROPERTY: attributeId
    public String getAttributeId() {
        return attributeId;
    }
    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    // PROPERTY: boostValue
    public int getBoostValue() {
        return boostValue;
    }
    public void setBoostValue(int boostValue) {
        this.boostValue = boostValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierAttributePair)) return false;

        SupplierAttributePair that = (SupplierAttributePair) o;

        if (!attributeId.equals(that.attributeId)) return false;
        if (!supplierId.equals(that.supplierId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = supplierId.hashCode();
        result = 31 * result + attributeId.hashCode();
        return result;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
