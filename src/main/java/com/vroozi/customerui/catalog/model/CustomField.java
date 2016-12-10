package com.vroozi.customerui.catalog.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CustomField implements Serializable {
    protected String id;
    protected String fieldName;
    protected String fieldType;
    protected boolean postFilterable;
    protected Integer displayOrder;
    protected String fieldDesc;
    protected String unitId;
    protected boolean deleted;
    protected boolean active;
    protected String defaultValue;
    protected String displayName;
    protected String mappingId;
    protected boolean searchable;
    protected boolean required;
    protected String icon;
    protected String searchBoost;
    protected boolean defaultPostFilter;
    protected Date lastUpdated;
    protected Date createdDate;
    protected List<String> catalogIds = new ArrayList<String>();
    protected String fieldId;
    private String typeToDisplay;
    private List<String> dropDownValues = new ArrayList<String>();


    public String getTypeToDisplay() {
		return typeToDisplay;
	}

	public void setTypeToDisplay(String typeToDisplay) {
		this.typeToDisplay = typeToDisplay;
	}

    public CustomField() {
    }

    public CustomField(Map<String, Object> map) {
        for(Map.Entry<String, Object> entry :map.entrySet()){
            if(entry.getKey().equals("id")) id  = (String)entry.getValue();
            else if(entry.getKey().equals("fieldName")) fieldName = (String)entry.getValue();
            else if(entry.getKey().equals("fieldType")) fieldType = (String)entry.getValue();
            else if(entry.getKey().equals("postFilterable")) postFilterable = (Boolean)entry.getValue();
            else if(entry.getKey().equals("displayOrder")) displayOrder = (Integer)entry.getValue();
            else if(entry.getKey().equals("fieldDesc")) fieldDesc = (String)entry.getValue();
            else if(entry.getKey().equals("unitId")) unitId = (String)entry.getValue();
            else if(entry.getKey().equals("deleted")) deleted  = (Boolean)entry.getValue();
            else if(entry.getKey().equals("active"))active  = (Boolean)entry.getValue();
            else if(entry.getKey().equals("defaultValue")) defaultValue = (String)entry.getValue();
            else if(entry.getKey().equals("displayName"))displayName  = (String)entry.getValue();
            else if(entry.getKey().equals("mappingId")) mappingId = (String)entry.getValue();
            else if(entry.getKey().equals("searchable")) searchable = (Boolean)entry.getValue();
            else if(entry.getKey().equals("required")) required = (Boolean)entry.getValue();
            else if(entry.getKey().equals("icon")) icon = (String)entry.getValue();
            else if(entry.getKey().equals("searchBoost")) searchBoost  = (String)entry.getValue();
            else if(entry.getKey().equals("defaultPostFilter")) defaultPostFilter = (Boolean)entry.getValue();
            else if(entry.getKey().equals("lastUpdated") && entry.getValue() != null) lastUpdated = new Date((Long)entry.getValue());
            else if(entry.getKey().equals("createdDate") && entry.getValue() != null) createdDate = new Date((Long)entry.getValue());
        }
    }


    public CustomField(CatalogCustomField catalogCustomField) {
        postFilterable = catalogCustomField.isPostFilterable();
        displayOrder = catalogCustomField.getDisplayOrder();
        defaultValue = catalogCustomField.getDefaultValue();
        mappingId = catalogCustomField.getMappingId();
        searchable = catalogCustomField.isSearchable();
        required = catalogCustomField.isRequired();
        lastUpdated = catalogCustomField.getLastUpdated();
        createdDate = catalogCustomField.getCreatedDate();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
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



    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSearchBoost() {
        return searchBoost;
    }

    public void setSearchBoost(String searchBoost) {

        if(searchBoost != null) {
            String[] tokens = searchBoost.split(",");
            for(String value : tokens) {
                if(value != null && value.length() > 0) {
                    this.searchBoost = value;
                }
            }
        } else {
            this.searchBoost = null;
        }
    }

    public boolean isDefaultPostFilter() {
        return defaultPostFilter;
    }

    public void setDefaultPostFilter(boolean defaultPostFilter) {
        this.defaultPostFilter = defaultPostFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomField that = (CustomField) o;

        if (active != that.active) return false;
        if (defaultPostFilter != that.defaultPostFilter) return false;
        if (deleted != that.deleted) return false;
        if (postFilterable != that.postFilterable) return false;
        if (required != that.required) return false;
        if (searchable != that.searchable) return false;
        if (unitId != that.unitId) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (defaultValue != null ? !defaultValue.equals(that.defaultValue) : that.defaultValue != null) return false;
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
        if (displayOrder != null ? !displayOrder.equals(that.displayOrder) : that.displayOrder != null) return false;
        if (fieldDesc != null ? !fieldDesc.equals(that.fieldDesc) : that.fieldDesc != null) return false;
        if (icon != null ? !icon.equals(that.icon) : that.icon != null) return false;
        if (lastUpdated != null ? !lastUpdated.equals(that.lastUpdated) : that.lastUpdated != null) return false;
        if (mappingId != null ? !mappingId.equals(that.mappingId) : that.mappingId != null) return false;
        if (searchBoost != null ? !searchBoost.equals(that.searchBoost) : that.searchBoost != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (postFilterable ? 1 : 0);
        result = 31 * result + (displayOrder != null ? displayOrder.hashCode() : 0);
        result = 31 * result + (fieldDesc != null ? fieldDesc.hashCode() : 0);
       // result = 31 * result + unitId;
        result = 31 * result + (deleted ? 1 : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (mappingId != null ? mappingId.hashCode() : 0);
        result = 31 * result + (searchable ? 1 : 0);
        result = 31 * result + (required ? 1 : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (searchBoost != null ? searchBoost.hashCode() : 0);
        result = 31 * result + (defaultPostFilter ? 1 : 0);
        result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }

    public List<String> getCatalogIds() {
        return catalogIds;
    }

    public void setCatalogIds(List<String> catalogIds) {
        this.catalogIds = catalogIds;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public List<String> getDropDownValues() {
        return dropDownValues;
    }

    public void setDropDownValues(List<String> dropDownValues) {
        this.dropDownValues = dropDownValues;
    }
}
