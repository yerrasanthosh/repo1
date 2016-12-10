package com.vroozi.customerui.catalog.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.vroozi.customerui.company.model.CommunicationMethod;
import com.vroozi.customerui.company.model.EncodingType;

public class EditCatalogForm {
    public static final long serialVersionUID = 1L;

    public String catalogName;

    private CommonsMultipartFile catalogFile;
    private CommonsMultipartFile imageFile;

    public String supplierName;
    public String externalCatalogId;
    public String catalogEditOption;

    private Boolean externalCatalog;
    private Boolean createVersion;
    
    public String clientCatalogId;
    public String startDate;
    public String endDate;

    public String catalogId;
    public String catalogTypeId;

    private String contractNumber;
    private String contractLineItem;

    private String extCatalogMethod;
    private String extCatalogImageField;
    private CommunicationMethod extCommunicationMethod;
    private EncodingType encodingType;
    
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
    public List<ExternalCatalogFields> fields= new LinkedList<ExternalCatalogFields>();


    public Boolean getExternalCatalog() {
		return externalCatalog;
	}
	public void setExternalCatalog(Boolean externalCatalog) {
		this.externalCatalog = externalCatalog;
	}
	//PROPERTY: extCatalogMethod
    public String getExtCatalogMethod() {
        return extCatalogMethod;
    }
    public void setExtCatalogMethod(String extCatalogMethod) {
        this.extCatalogMethod = extCatalogMethod;
    }

    //PROPERTY: fields
    public List<ExternalCatalogFields> getFields() {
        return fields;
    }
    public void setFields(List<ExternalCatalogFields> fields) {
        this.fields = fields;
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

    //PROPERTY: catalogName
    public String getCatalogName() {
        return catalogName;
    }
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    //PROPERTY: catalogFile
    public CommonsMultipartFile getCatalogFile() {
        return catalogFile;
    }
    public void setCatalogFile(CommonsMultipartFile catalogFile) {
        this.catalogFile = catalogFile;
    }

    //PROPERTY: imageFile
    public CommonsMultipartFile getImageFile() {
        return imageFile;
    }
    public void setImageFile(CommonsMultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    //PROPERTY: supplierId
    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierId) {
        this.supplierName = supplierId;
    }

    //PROPERTY: externalCatalogId
    public String getExternalCatalogId() {
        return externalCatalogId;
    }
    public void setExternalCatalogId(String externalCatalogId) {
        this.externalCatalogId = externalCatalogId;
    }

    //PROPERTY: catalogEditOption
    public String getCatalogEditOption() {
        return catalogEditOption;
    }
    public void setCatalogEditOption(String catalogEditOption) {
        this.catalogEditOption = catalogEditOption;
    }

    //PROPERTY: clientCatalogId
    public String getClientCatalogId() {
        return clientCatalogId;
    }
    public void setClientCatalogId(String clientCatalogId) {
        this.clientCatalogId = clientCatalogId;
    }

    //PROPERTY: startDate
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    //PROPERTY: endDate
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    //PROPERTY: catalogId
    public String getCatalogId() {
        return catalogId;
    }
    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    //PROPERTY: catalogTypeId
    public String getCatalogTypeId() {
        return catalogTypeId;
    }
    public void setCatalogTypeId(String catalogTypeId) {
        this.catalogTypeId = catalogTypeId;
    }
	public Boolean getCreateVersion() {
		return createVersion;
	}
	public void setCreateVersion(Boolean createVersion) {
		this.createVersion = createVersion;
	}

	public String getExtCatalogImageField() {
		return extCatalogImageField;
	}

	public void setExtCatalogImageField(String extCatalogImageField) {
		this.extCatalogImageField = extCatalogImageField;
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
    
    public int getTimeZoneId() {
      return timeZoneId;
    }

    public void setTimeZoneId(int timeZoneId) {
      this.timeZoneId = timeZoneId;
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

    public boolean isQuantityLocked() {
      return quantityLocked;
    }

    public void setQuantityLocked(boolean quantityLocked) {
      this.quantityLocked = quantityLocked;
    }
    
}
