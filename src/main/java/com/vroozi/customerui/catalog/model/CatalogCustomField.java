package com.vroozi.customerui.catalog.model;


import java.util.Date;
import java.util.Map;

public class CatalogCustomField {

    private String id;
    private String catalogId;
    private String customFieldId;
    protected boolean postFilterable;
    protected Integer displayOrder;
    protected boolean required;
    protected String defaultValue;
    protected String mappingId;
    protected boolean searchable;
    protected Date lastUpdated;
    protected Date createdDate;
    private String fieldName;
    private String fieldType;
    private boolean deleted;
    
    
    public CatalogCustomField() {

    }
    public CatalogCustomField(CustomField customField) {
        fieldName = customField.getFieldName();
        fieldType = customField.getFieldType();
        postFilterable = customField.isPostFilterable();
        displayOrder = customField.getDisplayOrder();
        defaultValue = customField.getDefaultValue();
        mappingId = customField.getMappingId();
        searchable = customField.isSearchable();
        required = customField.isRequired();
        lastUpdated = customField.getLastUpdated();
        createdDate = customField.getCreatedDate();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public CatalogCustomField(Map<String, Object> map) {
        for(Map.Entry<String, Object> entry :map.entrySet()){
            if(entry.getKey().equals("id")) id  = (String)entry.getValue();
            else if(entry.getKey().equals("catalogId")) catalogId = (String)entry.getValue();
            else if(entry.getKey().equals("fieldName")) fieldName = (String)entry.getValue();
            else if(entry.getKey().equals("fieldType")) fieldType = (String)entry.getValue();
            else if(entry.getKey().equals("customFieldId")) customFieldId = (String)entry.getValue();
            else if(entry.getKey().equals("postFilterable")) postFilterable = (Boolean)entry.getValue();
            else if(entry.getKey().equals("displayOrder")) displayOrder = (Integer)entry.getValue();
            else if(entry.getKey().equals("defaultValue")) defaultValue = (String)entry.getValue();
            else if(entry.getKey().equals("mappingId")) mappingId = (String)entry.getValue();
            else if(entry.getKey().equals("searchable")) searchable = (Boolean)entry.getValue();
            else if(entry.getKey().equals("required")) required = (Boolean)entry.getValue();
            else if(entry.getKey().equals("lastUpdated") && entry.getValue() != null) lastUpdated = new Date((Long)entry.getValue());
            else if(entry.getKey().equals("createdDate") && entry.getValue() != null) createdDate = new Date((Long)entry.getValue());
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPostFilterable() {
        return postFilterable;
    }

    public void setPostFilterable(boolean postFilterable) {
        this.postFilterable = postFilterable;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        if(defaultValue != null) {
            String[] tokens = defaultValue.split(",");
            for(String value : tokens) {
                if(value != null && value.length() > 0) {
                    this.defaultValue = value;
                }
            }
        } else {
            this.defaultValue = null;
        }
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        if(mappingId != null && mappingId.length() > 1) {
            String[] tokens = mappingId.split(",");
            for(String value : tokens) {
                if(Integer.parseInt(value) > 0) {
                    this.mappingId = value;
                }
            }
        } else if(mappingId != null && mappingId.length() == 1) {
            this.mappingId = mappingId;
        } else {
            this.mappingId = "0";
        }
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(String customFieldId) {
        this.customFieldId = customFieldId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


}
