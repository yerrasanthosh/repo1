package com.vroozi.customerui.catalog.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.vroozi.customerui.company.model.CommunicationMethod;
import com.vroozi.customerui.company.model.EncodingType;

public class EditCatalogPost {
    private int catalogTypeId;
    private String catalogName;
    private String catalogSourcePath;
    private String catalogOrigin;
    private String catalogId;
    private int catalogSubmitter;
    private int unitId;
    private String catalogImagesPath;
    private String supplierId;
    private int externalCatalogId;
    private String languageCode;
    private Date validFrom;
    private Date validUntil;
    private String catalogTimeZone;
    private String contractNumber;
    private String contractLineItem;
    private String catalogFileFormat;
    private String clientCatalogId;

    private String catalogDesc;
    private int revision=1;
    private String methodType;

    private boolean createNewVersion;
    private List<String> approverIds;
    private List<Integer> profileIds;
    
    public List<ExternalCatalogFields> fields= new LinkedList<ExternalCatalogFields>();
    private String extCatalogMethod;
    private String extCatalogImageField;
    private CommunicationMethod extCommunicationMethod;
    private EncodingType encodingType;
    private Boolean searchAllowed;
    private String quoteId;
    private String rfqNumber;
    
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

    public boolean isCreateNewVersion() {
		return createNewVersion;
	}
	public void setCreateNewVersion(boolean createNewVersion) {
		this.createNewVersion = createNewVersion;
	}
	public String getExtCatalogMethod() {
        return extCatalogMethod;
    }
    public void setExtCatalogMethod(String extCatalogMethod) {
        this.extCatalogMethod = extCatalogMethod;
    }

    public List<ExternalCatalogFields> getFields() {
        return fields;
    }
    public void setFields(List<ExternalCatalogFields> fields) {
        this.fields = fields;
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

  public String getCatalogDesc() {
        return catalogDesc;
    }
    public void setCatalogDesc(String catalogDesc) {
        this.catalogDesc = catalogDesc;
    }

    public String getCatalogFileFormat() {
        return catalogFileFormat;
    }
    public void setCatalogFileFormat(String catalogFileFormat) {
        this.catalogFileFormat = catalogFileFormat;
    }

    public String getCatalogId() {
        return catalogId;
    }
    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogImagesPath() {
        return catalogImagesPath;
    }
    public void setCatalogImagesPath(String catalogImagesPath) {
        this.catalogImagesPath = catalogImagesPath;
    }

    public String getCatalogName() {
        return catalogName;
    }
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogOrigin() {
        return catalogOrigin;
    }
    public void setCatalogOrigin(String catalogOrigin) {
        this.catalogOrigin = catalogOrigin;
    }

    public String getCatalogSourcePath() {
        return catalogSourcePath;
    }
    public void setCatalogSourcePath(String catalogSourcePath) {
        this.catalogSourcePath = catalogSourcePath;
    }

    public int getCatalogSubmitter() {
        return catalogSubmitter;
    }
    public void setCatalogSubmitter(int catalogSubmitter) {
        this.catalogSubmitter = catalogSubmitter;
    }

    public int getCatalogTypeId() {
        return catalogTypeId;
    }
    public void setCatalogTypeId(int catalogTypeId) {
        this.catalogTypeId = catalogTypeId;
    }

    public String getClientCatalogId() {
        return clientCatalogId;
    }
    public void setClientCatalogId(String clientCatalogId) {
        this.clientCatalogId = clientCatalogId;
    }

    public String getContractLineItem() {
        return contractLineItem;
    }
    public void setContractLineItem(String contractLineItem) {
        this.contractLineItem = contractLineItem;
    }

    public String getContractNumber() {
        return contractNumber;
    }
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getLanguageCode() {
        return languageCode;
    }
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getMethodType() {
        return methodType;
    }
    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public int getRevision() {
        return revision;
    }
    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getUnitId() {
        return unitId;
    }
    public void setUnitId(int unitId) {
        this.unitId = unitId;
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
    
    public String getCatalogTimeZone() {
      return catalogTimeZone;
    }

    public void setCatalogTimeZone(String catalogTimeZone) {
      this.catalogTimeZone = catalogTimeZone;
    }
    
    public int getExternalCatalogId() {
        return externalCatalogId;
    }

    public void setExternalCatalogId(int externalCatalogId) {
        this.externalCatalogId = externalCatalogId;
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
