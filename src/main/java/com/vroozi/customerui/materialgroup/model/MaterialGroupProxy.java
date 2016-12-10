package com.vroozi.customerui.materialgroup.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class MaterialGroupProxy {
	
	
	private int row;
	
	private String catalogCategoryCode;
    private String companyCategoryCode;
    private String companyLabel;
    private String supplierId;
    private String parent;
    private int unitId;
    private String level1Val;
    private String level2Val;
    private String level3Val;
    private String uniqueSystemId;
    private String comments;
    private String description;
    private boolean display;
    private List<String> contentViews;
    private String contentViewsToDisplay;
    
    
    List<MaterialGroupProxy> childs ;

	public int getUnitId() {
		return unitId;
	}
	public List<MaterialGroupProxy> getChilds() {
		return childs;
	}
	public void setChilds(List<MaterialGroupProxy> childs) {
		this.childs = childs;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getCatalogCategoryCode() {
		return catalogCategoryCode;
	}
	public void setCatalogCategoryCode(String catalogCategoryCode) {
		this.catalogCategoryCode = catalogCategoryCode;
	}
	public String getCompanyCategoryCode() {
		return companyCategoryCode;
	}
	public void setCompanyCategoryCode(String companyCategoryCode) {
		this.companyCategoryCode = companyCategoryCode;
	}
	public String getCompanyLabel() {
		return companyLabel;
	}
	public void setCompanyLabel(String companyLabel) {
		this.companyLabel = companyLabel;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String vendorNumber) {
		this.supplierId = vendorNumber;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
    public List<String> getContentViews() {
       return this.contentViews;
    }
  
    public void setContentViews(List<String> contentViews) {
      this.contentViews = contentViews;
    }
    
    public String getContentViewsToDisplay() {
      return contentViewsToDisplay;
    }
    public void setContentViewsToDisplay(String contentViewsToDisplay) {
      this.contentViewsToDisplay = contentViewsToDisplay;
    }
    public String getLevel1Val() {
        return level1Val;
    }

    public void setLevel1Val(String level1Val) {
        this.level1Val = level1Val;
    }

    public String getLevel2Val() {
        return level2Val;
    }

    public void setLevel2Val(String level2Val) {
        this.level2Val = level2Val;
    }

    public String getLevel3Val() {
        return level3Val;
    }

    public void setLevel3Val(String level3Val) {
        this.level3Val = level3Val;
    }
    
    public String getUniqueSystemId() {
        return uniqueSystemId;
    }
    
    public void setUniqueSystemId(String uniqueSystemId) {
        this.uniqueSystemId = uniqueSystemId;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
}
