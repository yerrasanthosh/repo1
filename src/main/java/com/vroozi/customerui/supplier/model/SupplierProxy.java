package com.vroozi.customerui.supplier.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Provides ....
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 10/10/12:9:47 AM
 */
public class SupplierProxy  implements Serializable {
    private static final long serialVersionUID = 1L;

    private String companyId;
    private String companyName;
    private String vendorId;
    private String alternateVendorId;
    private String address1;
    private String address2;
    private String state;
    private String country;
    private String dunsNumber;
    private String unitId;
    private String logo;
    private boolean punchOut;
    private String createdOnView;
    private String minOrder;
    private boolean includeSupplierCard;
    private boolean disableBrowse;
    private String currencyCode;
    private String defaultVendorId;
    private String phone;
    private String fax;
    private String contactName;
    private String contactRole;
    private String email;
    private String city;
    private String zip;
    private String language;
    private String dateFormat;
    private String decimalNotation;
    private String timeZone;
    private String createdBy;
    private String createdByName;
    private Date createdOn;
    private boolean active = false;
    private String taxId;
    private String paymentTerms;
    private boolean buyRoute;
    private boolean nonCatalogSupplier;
    private List<String> approverIds;
    private List<Integer> assignedContentViews;
    private List<SupplierAttributePair> supplierAttributePairs;
    private List<Integer> systemVendorIds;
    private List<Integer> companyUserIds;

    
    private List<String> supplierAdmins;
    
    private String uniqueSupplierId;

    private String sendPurchaseOrdersBy;
    private boolean singleSourceSupplier;
    
    private CxmlDetails cxmlDetails;

    public String getSendPurchaseOrdersBy() {
        return sendPurchaseOrdersBy;
    }

    public void setSendPurchaseOrdersBy(String sendPurchaseOrdersBy) {
        this.sendPurchaseOrdersBy = sendPurchaseOrdersBy;
    }

    public String getUniqueSupplierId() {
        return uniqueSupplierId;
    }

    public void setUniqueSupplierId(String uniqueSupplierId) {
        this.uniqueSupplierId = uniqueSupplierId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAlternateVendorId() {
        return alternateVendorId;
    }
    public void setAlternateVendorId(String alternateVendorId) {
        this.alternateVendorId = alternateVendorId;
    }

    public List<String> getApproverIds() {
        return approverIds;
    }
    public void setApproverIds(List<String> approverIds) {
        this.approverIds = approverIds;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public List<SupplierAttributePair> getSupplierAttributePairs() {
        return supplierAttributePairs;
    }
    public void setSupplierAttributePairs(List<SupplierAttributePair> supplierAttributePairs) {
        this.supplierAttributePairs = supplierAttributePairs;
    }

    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Integer> getCompanyUserIds() {
        return companyUserIds;
    }
    public void setCompanyUserIds(List<Integer> companyUserIds) {
        this.companyUserIds = companyUserIds;
    }

    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    public List<Integer> getAssignedContentViews() {
		return assignedContentViews;
	}

	public void setAssignedContentViews(List<Integer> assignedContentViews) {
		this.assignedContentViews = assignedContentViews;
	}

	public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByName() {
        return createdByName;
    }
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedOnView() {
        return createdOnView;
    }
    public void setCreatedOnView(String createdOnView) {
        this.createdOnView = createdOnView;
    }

    public String getDateFormat() {
        return dateFormat;
    }
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDecimalNotation() {
        return decimalNotation;
    }
    public void setDecimalNotation(String decimalNotation) {
        this.decimalNotation = decimalNotation;
    }

    public String getDefaultVendorId() {
        return defaultVendorId;
    }
    public void setDefaultVendorId(String defaultVendorId) {
        this.defaultVendorId = defaultVendorId;
    }

    public String getDunsNumber() {
        return dunsNumber;
    }
    public void setDunsNumber(String dunsNumber) {
        this.dunsNumber = dunsNumber;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMinOrder() {
        return minOrder;
    }
    public void setMinOrder(String minOrder) {
        this.minOrder = minOrder;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPunchOut() {
        return punchOut;
    }
    public void setPunchOut(boolean punchOut) {
        this.punchOut = punchOut;
    }

    public List<Integer> getSystemVendorIds() {
        return systemVendorIds;
    }
    public void setSystemVendorIds(List<Integer> systemVendorIds) {
        this.systemVendorIds = systemVendorIds;
    }

    public String getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getUnitId() {
        return unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getVendorId() {
        return vendorId;
    }
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getContactRole() {
        return contactRole;
    }
    public void setContactRole(String contactRole) {
        this.contactRole = contactRole;
    }

    public boolean isIncludeSupplierCard() {
        return includeSupplierCard;
    }
    public void setIncludeSupplierCard(boolean includeSupplierCard) {
        this.includeSupplierCard = includeSupplierCard;
    }

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public List<String> getSupplierAdmins() {
		return supplierAdmins;
	}

	public void setSupplierAdmins(List<String> supplierAdmins) {
		this.supplierAdmins = supplierAdmins;
	}


    public boolean isDisableBrowse() {
        return disableBrowse;
    }

    public void setDisableBrowse(boolean disableBrowse) {
        this.disableBrowse = disableBrowse;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public boolean isBuyRoute() {
        return buyRoute;
    }

    public void setBuyRoute(boolean buyRoute) {
        this.buyRoute = buyRoute;
    }

    public boolean isNonCatalogSupplier() {
        return nonCatalogSupplier;
    }

    public void setNonCatalogSupplier(boolean nonCatalogSupplier) {
        this.nonCatalogSupplier = nonCatalogSupplier;
    }

    public void setSingleSourceSupplier(boolean singleSourceSupplier) {
        this.singleSourceSupplier = singleSourceSupplier;
    }

    public Boolean isSingleSourceSupplier() {
        return singleSourceSupplier;
    }
    
    public CxmlDetails getCxmlDetails() {
      return cxmlDetails;
    }

    public void setCxmlDetails(CxmlDetails cxmlDetails) {
      this.cxmlDetails = cxmlDetails;
    }
    
    public static class CxmlDetails {
      private String fromDomain;
      private String fromIdentity;
      private String toDomain;
      private String toIdentity;
      private String senderDomain;
      private String senderIdentity;
      private String sharedSecret;
      private String orderSubmissionUrl;

      public String getFromIdentity() {
        return fromIdentity;
      }

      public void setFromIdentity(String fromIdentity) {
        this.fromIdentity = fromIdentity;
      }

      public String getToIdentity() {
        return toIdentity;
      }

      public void setToIdentity(String toIdentity) {
        this.toIdentity = toIdentity;
      }

      public String getSenderIdentity() {
        return senderIdentity;
      }

      public void setSenderIdentity(String senderIdentity) {
        this.senderIdentity = senderIdentity;
      }

      public String getSharedSecret() {
        return sharedSecret;
      }

      public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
      }

      public String getOrderSubmissionUrl() {
        return orderSubmissionUrl;
      }

      public void setOrderSubmissionUrl(String orderSubmissionUrl) {
        this.orderSubmissionUrl = orderSubmissionUrl;
      }

      public String getFromDomain() {
        return fromDomain;
      }

      public void setFromDomain(String fromDomain) {
        this.fromDomain = fromDomain;
      }

      public String getToDomain() {
        return toDomain;
      }

      public void setToDomain(String toDomain) {
        this.toDomain = toDomain;
      }

      public String getSenderDomain() {
        return senderDomain;
      }

      public void setSenderDomain(String senderDomain) {
        this.senderDomain = senderDomain;
      }

      @Override
      public String toString() {
        return "CxmlDetails [fromDomain=" + fromDomain + ", fromIdentity=" + fromIdentity
            + ", toDomain=" + toDomain + ", toIdentity=" + toIdentity + ", senderDomain="
            + senderDomain + ", senderIdentity=" + senderIdentity + ", sharedSecret=" + sharedSecret
            + ", orderSubmissionUrl=" + orderSubmissionUrl + "]";
      }
    }
}
