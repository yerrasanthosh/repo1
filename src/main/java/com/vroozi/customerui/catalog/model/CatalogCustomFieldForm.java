package com.vroozi.customerui.catalog.model;

import java.util.*;

import com.vroozi.customerui.catalogItem.model.OciItemCustomFieldValue;

public class CatalogCustomFieldForm {

    protected String id;
    private List<String> catalogCustomFieldId = new ArrayList<String>();
    private String catalogIds;
    private String catalogId;
    private List<String> customFieldIdList = new ArrayList<String>();
    private String customFieldId;
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
    protected String fieldIds;
    protected String search;
    protected String status;
    protected String orderBy;
    private String fieldId;
    
    private List<String> catalogCustomRequired = new ArrayList<String>();
    private List<String> catalogCustomPostFilterable = new ArrayList<String>();
	private List<String> catalogCustomSearchable = new ArrayList<String>();
	private List<String> mappingIdList = new ArrayList<String>();
    private List<String> dropDownValues = new ArrayList<String>();
	
    public List<String> getMappingIdList() {
		return mappingIdList;
	}

	public void setMappingIdList(List<String> mappingIdList) {
		this.mappingIdList = mappingIdList;
	}

	public List<String> getCatalogCustomPostFilterable() {
		return catalogCustomPostFilterable;
	}

	public void setCatalogCustomPostFilterable(
			List<String> catalogCustomPostFilterable) {
		this.catalogCustomPostFilterable = catalogCustomPostFilterable;
	}

	public List<String> getCatalogCustomSearchable() {
		return catalogCustomSearchable;
	}

	public void setCatalogCustomSearchable(List<String> catalogCustomSearchable) {
		this.catalogCustomSearchable = catalogCustomSearchable;
	}
    public List<String> getCatalogCustomRequired() {
		return catalogCustomRequired;
	}

	public void setCatalogCustomRequired(List<String> catalogCustomRequired) {
		this.catalogCustomRequired = catalogCustomRequired;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFieldIds() {
		return fieldIds;
	}

	public void setFieldIds(String fieldIds) {
		this.fieldIds = fieldIds;
	}

	public CatalogCustomFieldForm() {
    }

    public List<String> getCustomFieldIdList() {
        return customFieldIdList;
    }

    public void setCustomFieldIdList(List<String> customFieldIdList) {
        this.customFieldIdList = customFieldIdList;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(String customFieldId) {
        this.customFieldId = customFieldId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

            this.defaultValue = defaultValue;
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
            this.searchBoost = searchBoost;
    }


    public boolean isDefaultPostFilter() {
        return defaultPostFilter;
    }

    public void setDefaultPostFilter(boolean defaultPostFilter) {
        this.defaultPostFilter = defaultPostFilter;
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

    public List<String> getCatalogCustomFieldId() {
        return catalogCustomFieldId;
    }

    public void setCatalogCustomFieldId(List<String> catalogCustomFieldId) {
        this.catalogCustomFieldId = catalogCustomFieldId;
    }

    public CatalogCustomFieldForm(Map<String, Object> map) {
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
            else if(entry.getKey().equals("catalogId")) catalogId = (String)entry.getValue();
            else if(entry.getKey().equals("customFieldId")) customFieldId = (String)entry.getValue();
        }
    }


    public CustomField getCustomField() {
        CustomField customField = new CustomField();
        customField.setId(getCustomFieldId());
        customField.setActive(isActive());
        customField.setCreatedDate(getCreatedDate());
        customField.setDefaultPostFilter(isDefaultPostFilter());
        customField.setDefaultValue(getDefaultValue());
        customField.setDeleted(isDeleted());
        customField.setDisplayName(getDisplayName());
        customField.setFieldDesc(getFieldDesc());
        customField.setDisplayOrder(getDisplayOrder());
        customField.setFieldName(getFieldName());
        customField.setFieldType(getFieldType());
        customField.setMappingId(getMappingId());
        customField.setSearchable(isSearchable());
        customField.setRequired(isRequired());
        customField.setSearchBoost(getSearchBoost());
        customField.setPostFilterable(isPostFilterable());
        customField.setLastUpdated(getLastUpdated());
        customField.setIcon(getIcon());
        customField.setUnitId(getUnitId());
        customField.setDisplayOrder(getDisplayOrder());
        return customField;
    }


    public CatalogCustomField getCatalogCustomField() {
        CatalogCustomField catalogCustomField = new CatalogCustomField();
        catalogCustomField.setCatalogId(getCatalogId());
        catalogCustomField.setCustomFieldId(getCustomFieldId());
        catalogCustomField.setFieldName(getFieldName());
        catalogCustomField.setFieldType(getFieldType());
        catalogCustomField.setCreatedDate(getCreatedDate());
        catalogCustomField.setLastUpdated(getLastUpdated());
        catalogCustomField.setSearchable(isSearchable());
        catalogCustomField.setRequired(isRequired());
        catalogCustomField.setPostFilterable(isPostFilterable());
        catalogCustomField.setDisplayOrder(getDisplayOrder());
        catalogCustomField.setDefaultValue(getDefaultValue());
        catalogCustomField.setMappingId(getMappingId());
        catalogCustomField.setId(getId() == null ? getCustomFieldId() : "");

        return catalogCustomField;
    }

    public OciItemCustomFieldValue getOciItemCustomFieldValue() {
        OciItemCustomFieldValue catalogCustomField = new OciItemCustomFieldValue();
        catalogCustomField.setCustomFieldId(getCustomFieldId());
        catalogCustomField.setFieldName(getFieldName());
        catalogCustomField.setFieldType(getFieldType());
        catalogCustomField.setDisplayOrder(getDisplayOrder());
        catalogCustomField.setActive(isActive());
        catalogCustomField.setDeleted(isDeleted());
        catalogCustomField.setValue(defaultValue);
        return catalogCustomField;
    }
    
    public void setCatalogCustomField(CatalogCustomField catalogCustomField) {
        this.id = catalogCustomField.getId();
        catalogCustomField.getCustomFieldId();
        this.catalogId = catalogCustomField.getCatalogId();
        this.createdDate = catalogCustomField.getCreatedDate();
        this.defaultValue = catalogCustomField.getDefaultValue();
        this.displayOrder = catalogCustomField.getDisplayOrder();
        this.fieldName = catalogCustomField.getFieldName();
        this.fieldType = catalogCustomField.getFieldType();
        this.lastUpdated = catalogCustomField.getLastUpdated();
        this.mappingId = catalogCustomField.getMappingId();
        this.postFilterable = catalogCustomField.isPostFilterable();
        this.required = catalogCustomField.isRequired();
        this.searchable = catalogCustomField.isSearchable();
    }


    public void setCustomField(CustomField customField) {
        customField.getCreatedDate();
        customField.getDefaultValue();
        this.displayName = customField.getDisplayName();
        customField.getDisplayOrder();
        this.fieldDesc = customField.getFieldDesc();
        this.fieldName = customField.getFieldName();
        this.fieldType = customField.getFieldType();
        this.icon = customField.getIcon();
        this.customFieldId = customField.getId();
        customField.getLastUpdated();
        customField.getMappingId();
        this.searchBoost = customField.getSearchBoost();
        this.unitId = customField.getUnitId();
        this.active = customField.isActive();
        this.defaultPostFilter = customField.isDefaultPostFilter();
        this.deleted = customField.isDeleted();
        this.setPostFilterable(customField.isPostFilterable());
        this.setRequired(customField.isRequired());
        this.setSearchable(customField.isSearchable());
        this.setDefaultPostFilter(customField.isDefaultPostFilter());
        this.defaultValue = customField.getDefaultValue();
        this.mappingId = customField.getMappingId();
        this.icon = customField.getIcon();
        this.setDropDownValues(customField.getDropDownValues());

    }


    public String getCatalogCustomFieldFormJSON() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"id\":\"" + id + "\",");
        stringBuilder.append("\"fieldName\":\"" + fieldName + "\",");
        stringBuilder.append("\"displayName\":\"" + displayName + "\",");
        stringBuilder.append("\"fieldDesc\":\"" + fieldDesc + "\",");
        stringBuilder.append("\"fieldType\":\"" + fieldType + "\",");
        stringBuilder.append("\"searchBoost\":\"" + searchBoost + "\",");
        stringBuilder.append("\"catalogId\":\"" + catalogId + "\",");
        stringBuilder.append("\"postFilterable\":" + postFilterable + ",");
        stringBuilder.append("\"defaultPostFilter\":" + defaultPostFilter + ",");
        stringBuilder.append("\"displayOrder\":\"" + displayOrder + "\",");
        stringBuilder.append("\"required\":" + required + ",");
        stringBuilder.append("\"defaultValue\":\"" + defaultValue + "\",");
        stringBuilder.append("\"mappingId\":\"" + mappingId + "\",");
        stringBuilder.append("\"searchable\":" + searchable + ",");
        stringBuilder.append("\"customFieldId\":\"" + customFieldId + "\",");
        stringBuilder.append("\"icon\":\"" + icon + "\"");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }


    public List<CatalogCustomField> getCatalogCustomFieldList() {
        List<CatalogCustomField> catalogCustomFieldList = new ArrayList<CatalogCustomField>();

        for(String aCatalogCustomField : catalogCustomFieldId) {
            CatalogCustomField catalogCustomField = getCatalogCustomField();
            catalogCustomField.setId(aCatalogCustomField);
            catalogCustomFieldList.add(catalogCustomField);
        }

        return catalogCustomFieldList;
    }


    public String getCatalogCustomFieldIds() {

        StringBuilder catalogCustomFieldIds = new StringBuilder();

        int size = catalogCustomFieldId.size();

        for(int i = 0; i < size; i++) {
            if(i > 0) catalogCustomFieldIds.append(",");
            catalogCustomFieldIds.append(catalogCustomFieldId.get(i));
        }

        return catalogCustomFieldIds.toString();
    }

    public List<CustomField> getCustomFields(Collection<CustomField> customFieldCollection, Set<String> catalogCustomFieldIdSet, boolean isExternal) {

        List<CustomField> customFieldList = new ArrayList<CustomField>();

        for(CustomField customField : customFieldCollection) {
        	if(isExternal && !customField.getFieldType().equals("list") && !customField.getFieldType().equals("flag") ){
	            if(!catalogCustomFieldIdSet.contains(customField.getId())) {
	                customFieldList.add(customField);
	            }
        	}else if(!isExternal){
        		if(!catalogCustomFieldIdSet.contains(customField.getId())) {
        			customFieldList.add(customField);
        		}
        	}
        }

        return customFieldList;
    }

    public String getCustomFieldFormJSON(List<CustomField> customFields) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"customField\": [");

        for(CustomField customField : customFields) {

            stringBuilder.append("{\"id\":\"" + customField.getId() + "\",");
            stringBuilder.append("\"fieldName\":\"" + customField.getFieldName() + "\"},");
        }

        stringBuilder.append("]}");
        return stringBuilder.toString();
    }

    public String getCatalogIds() {
        return catalogIds;
    }



    public void setCatalogIds(String catalogIds) {
        this.catalogIds = catalogIds;
    }

    public List<String> getDropDownValues() {
        return dropDownValues;
    }

    public void setDropDownValues(List<String> dropDownValues) {
        this.dropDownValues = dropDownValues;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }
}