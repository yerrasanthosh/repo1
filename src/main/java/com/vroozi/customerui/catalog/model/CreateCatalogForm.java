package com.vroozi.customerui.catalog.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.vroozi.customerui.company.model.CommunicationMethod;
import com.vroozi.customerui.company.model.EncodingType;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CreateCatalogForm implements Serializable {
    public static final long serialVersionUID = -3266765822362783129L;

    public String supplierName;
    public String catalogName;
    public String catalogNameNoSpace;
    public String resourceId;
    public String externalCatalogId;
    public String catalogId;
    public String clientCatalogId;
    public String createdOn;
    public int createdBy;
    public int unitId;
    public int numberOfItems;
    public boolean movedToOffline;
    public boolean approved;
    public boolean active;
    public boolean saved;
    public String startDate;
    public String endDate;

    private CommonsMultipartFile catalogFile;
    private CommonsMultipartFile imageFile;

    public String activationHour;
    private String extCatalogMethod;
    private String contractNumber;
    private String contractLineItem;
    private String extCatalogImageField;
    private CommunicationMethod extCommunicationMethod;
    private EncodingType encodingType;

    private Boolean externalCatalog;
    private Boolean searchAllowed;
    
    private List<String> approverIds;
    private List<Integer> profileIds;

    private String quoteId;
    private String rfqNumber;
    private int timeZoneId;
    private boolean quantityLocked;
    
    
    public List<Integer> getProfileIds() {
		return profileIds;
	}

	public void setProfileIds(List<Integer> profileIds) {
		this.profileIds = profileIds;
	}

	public List<String> getApproverIds() {
		return approverIds;
	}

	public void setApproverIds(List<String> approverIds) {
		this.approverIds = approverIds;
	}
    public Boolean getSearchAllowed() {
		return searchAllowed;
	}

	public void setSearchAllowed(Boolean searchAllowed) {
		this.searchAllowed = searchAllowed;
	}
    
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractLineItem() {
        return contractLineItem;
    }

    public void setContractLineItem(String contractLineItem) {
        this.contractLineItem = contractLineItem;
    }

    public String getExtCatalogMethod() {
        return extCatalogMethod;
    }

    public void setExtCatalogMethod(String extCatalogMethod) {
        this.extCatalogMethod = extCatalogMethod;
    }

    public List<ExternalCatalogFields> fields= new LinkedList<ExternalCatalogFields>();


    public List<ExternalCatalogFields> getFields() {
        return fields;
    }

    public void setFields(List<ExternalCatalogFields> fields) {
        this.fields = fields;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
    public String getCatalogNameNoSpace() {
        return catalogNameNoSpace;
    }

    public void setCatalogNameNoSpace(String catalogNameNoSpace) {
        this.catalogNameNoSpace = catalogNameNoSpace;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public boolean isMovedToOffline() {
        return movedToOffline;
    }

    public void setMovedToOffline(boolean movedToOffline) {
        this.movedToOffline = movedToOffline;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }



    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getActivationHour() {
        return activationHour;
    }

    public void setActivationHour(String activationHour) {
        this.activationHour = activationHour;
    }
    
//    public void setValueObject(CatalogSummary catalog){
//        supplierName=catalog.getSupplierName();
//        catalogName=catalog.getName();
//        catalogNameNoSpace=catalog.getCatalogNameNoSpace();
//        unitId=catalog.getUnitId();
//        catalogId=(catalog.getCatalogId());
//        approved=catalog.isApproved();
//        active=catalog.isActive();
//        startDate=catalog.getStartDate();
//        endDate=catalog.getEndDate();
//    }

    public CommonsMultipartFile getCatalogFile() {
        return catalogFile;
    }

    public void setCatalogFile(CommonsMultipartFile catalogFile) {
        this.catalogFile = catalogFile;
    }

    public CommonsMultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(CommonsMultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getClientCatalogId() {
        return clientCatalogId;
    }

    public void setClientCatalogId(String clientCatalogId) {
        this.clientCatalogId = clientCatalogId;
    }

    public String getExternalCatalogId() {
        return externalCatalogId;
    }

    public void setExternalCatalogId(String externalCatalogId) {
        this.externalCatalogId = externalCatalogId;
    }

    public Boolean getExternalCatalog() {
        return externalCatalog;
    }
    public void setExternalCatalog(Boolean externalCatalog) {
        this.externalCatalog = externalCatalog;
    }

	public String getExtCatalogImageField() {
		return extCatalogImageField;
	}

	public void setExtCatalogImageField(String extCatalogImageField) {
		this.extCatalogImageField = extCatalogImageField;
	}

	public CommunicationMethod getExtCommunicationMethod() {
        return extCommunicationMethod;
    }

    public void setExtCommunicationMethod(CommunicationMethod extCommunicationMethod) {
        this.extCommunicationMethod = extCommunicationMethod;
    }
    
    public EncodingType getEncodingType() {
      return encodingType;
    }

    public void setEncodingType(EncodingType encodingType) {
      this.encodingType = encodingType;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getRfqNumber() {
        return rfqNumber;
    }
    
    public int getTimeZoneId() {
      return timeZoneId;
    }

    public void setTimeZoneId(int timeZoneId) {
      this.timeZoneId = timeZoneId;
    }

    public void setRfqNumber(String rfqNumber) {
        this.rfqNumber = rfqNumber;
    }

    public boolean isQuantityLocked() {
      return quantityLocked;
    }

    public void setQuantityLocked(boolean quantityLocked) {
      this.quantityLocked = quantityLocked;
    }

}
