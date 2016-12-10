package com.vroozi.customerui.catalog.model;

import java.util.Date;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.vroozi.customerui.company.model.CommunicationMethod;
import com.vroozi.customerui.company.model.EncodingType;

public class CatalogProxy implements Serializable {
    private String catalogId;
    private String name;
    private String unitId;
    private int catalogTypeId;


    private int revision;
    private String catalogOrigin;
    private String catalogTypeDesc;
    private int catalogStateId;
    private int catalogFormatId;
    private String catalogDescription;
    private String supplierId;
    private String submitterId;
    private boolean active;
    private String languageCode;
    private Date createdOn;
    private Date lastUpdated;
    private String lastUpdatedBy;
    private Date validFrom;
    private Date validUntil;
    private String catalogTimeZone;

    private String contractNumber;
    private String vendorId;
    private String parentCatalogId;
    private String contractLineItem;
    private boolean deleted;
    private String catalogState;
    private boolean locked;
    private String lockedReason;
    private Date lockedTimeStamp;

    private String catalogStatus;


    private Integer approvedItems;
    private Integer rejectedItems;
    private Integer pendingItems;
    private Integer publishedItems;
    private Integer inputRecords;
    private Integer outputRecords;
    public Integer failedRecords;
    private boolean containsCustomFields;
    private String clientCatalogId;
    private String externalCatalogId;
    private String productDetailView;
    private String httpRequestMethod;
    private String errorDescription;
    private String extCatalogMethod;
    private CommunicationMethod extCommunicationMethod;
    private EncodingType encodingType;
    private String extCatalogImageField;
    private boolean publishItem;

    private Integer loadedRecords;

    private String updateType;
    private boolean searchAllowed;
    private String quoteId;
    private String rfqNumber;
    private String merged;
    
    private boolean quantityLocked;
    
    public List<ExternalCatalogFields> fields= new LinkedList<ExternalCatalogFields>();
    public List<String> approvers= null;

    
	public String getMerged() {
		return merged;
	}

	public void setMerged(String merged) {
		this.merged = merged;
	}
    
    public boolean isSearchAllowed() {
		return searchAllowed;
	}

	public void setSearchAllowed(boolean searchAllowed) {
		this.searchAllowed = searchAllowed;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public List<String> getApprovers() {
        return approvers;
    }

    public void setApprovers(List<String> approvers) {
        this.approvers = approvers;
    }

    public String getExtCatalogMethod() {
        return extCatalogMethod;
    }

    public void setExtCatalogMethod(String extCatalogMethod) {
        this.extCatalogMethod = extCatalogMethod;
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

    public String getExtCatalogImageField() {
		return extCatalogImageField;
	}

	public void setExtCatalogImageField(String extCatalogImageField) {
		this.extCatalogImageField = extCatalogImageField;
	}

	public List<ExternalCatalogFields> getFields() {
        return fields;
    }

    public void setFields(List<ExternalCatalogFields> fields) {
        this.fields = fields;
    }
    
    public String getCatalogTimeZone() {
        return catalogTimeZone;
    } 

    public void setCatalogTimeZone(String catalogTimeZone) {
        this.catalogTimeZone = catalogTimeZone;
    }
    
    public String getExternalCatalogId() {
        return externalCatalogId;
    }

    public void setExternalCatalogId(String externalCatalogId) {
        this.externalCatalogId = externalCatalogId;
    }
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getClientCatalogId() {
        return clientCatalogId;
    }

    public void setClientCatalogId(String clientCatalogId) {
        this.clientCatalogId = clientCatalogId;
    }

    public boolean isContainsCustomFields() {
        return containsCustomFields;
    }

    public void setContainsCustomFields(boolean containsCustomFields) {
        this.containsCustomFields = containsCustomFields;
    }

    public String getParentCatalogId() {
        return parentCatalogId;
    }

    public void setParentCatalogId(String parentCatalogId) {
        this.parentCatalogId = parentCatalogId;
    }
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getLockedReason() {
        return lockedReason;
    }

    public void setLockedReason(String lockedReason) {
        this.lockedReason = lockedReason;
    }
    public Date getLockedTimeStamp() {
        return lockedTimeStamp;
    }

    public void setLockedTimeStamp(Date lockedTimeStamp) {
        this.lockedTimeStamp = lockedTimeStamp;
    }

    public String getCatalogOrigin() {
        return catalogOrigin;
    }

    public void setCatalogOrigin(String catalogOrigin) {
        this.catalogOrigin = catalogOrigin;
    }

    public String getCatalogTypeDesc() {

        return catalogTypeDesc;
    }

    public void setCatalogTypeDesc(String catalogTypeDesc) {
        this.catalogTypeDesc = catalogTypeDesc;
    }

    public int getCatalogStateId() {
        return catalogStateId;
    }

    public void setCatalogStateId(int catalogStateId) {
        this.catalogStateId = catalogStateId;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCatalogTypeId() {
        return catalogTypeId;
    }

    public void setCatalogTypeId(int catalogTypeId) {
        this.catalogTypeId = catalogTypeId;
    }

    public int getCatalogFormatId() {
        return catalogFormatId;
    }

    public void setCatalogFormatId(int catalogFormatId) {
        this.catalogFormatId = catalogFormatId;
    }

    public String getCatalogDescription() {
        return catalogDescription;
    }

    public void setCatalogDescription(String catalogDescription) {
        this.catalogDescription = catalogDescription;
    }


    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
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

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCatalogStatus() {
        return catalogStatus;
    }

    public void setCatalogStatus(String catalogStatus) {
        this.catalogStatus = catalogStatus;
    }


    public String getCatalogState() {
        return catalogState;
    }

    public void setCatalogState(String catalogState) {
        this.catalogState = catalogState;
    }

    public String getProductDetailView() {
        return productDetailView;
    }

    public void setProductDetailView(String productDetailView) {
        this.productDetailView = productDetailView;
    }

    public String getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public void setHttpRequestMethod(String httpRequestMethod) {
        this.httpRequestMethod = httpRequestMethod;
    }

    public Integer getApprovedItems() {
        return approvedItems;
    }
    public void setApprovedItems(Integer approvedItems) {
        this.approvedItems = approvedItems;
    }

    public Integer getRejectedItems() {
        return rejectedItems;
    }
    public void setRejectedItems(Integer rejectedItems) {
        this.rejectedItems = rejectedItems;
    }

    public Integer getPendingItems() {
        return pendingItems;
    }
    public void setPendingItems(Integer pendingItems) {
        this.pendingItems = pendingItems;
    }

    public Integer getPublishedItems() {
        return publishedItems;
    }
    public void setPublishedItems(Integer publishedItems) {
        this.publishedItems = publishedItems;
    }

    public Integer getInputRecords() {
        return inputRecords;
    }
    public void setInputRecords(Integer inputRecords) {
        this.inputRecords = inputRecords;
    }

    public Integer getOutputRecords() {
        return outputRecords;
    }
    public void setOutputRecords(Integer outputRecords) {
        this.outputRecords = outputRecords;
    }

    public Integer getFailedRecords() {
        return failedRecords;
    }
    public void setFailedRecords(Integer failedRecords) {
        this.failedRecords = failedRecords;
    }

    public void reset(){
        this.setOutputRecords(0);
        this.setApprovedItems(0);
        //this.setCatalogStatus(1);
        this.setLocked(false);
        this.setPendingItems(0);
        this.setPublishedItems(0);
        this.setRejectedItems(0);

    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }


    /*public boolean isActive() {
        return active;
    }*/

    public boolean isPublishItem() {
        return publishItem;
    }

    public void setPublishItem(boolean publishItem) {
        this.publishItem = publishItem;
    }

    public Integer getLoadedRecords() {
        return loadedRecords;
    }

    public void setLoadedRecords(Integer loadedRecords) {
        this.loadedRecords = loadedRecords;
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
