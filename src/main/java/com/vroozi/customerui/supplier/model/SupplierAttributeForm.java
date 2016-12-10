package com.vroozi.customerui.supplier.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Provides form for creating supplier attributes.
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 10/22/12:12:51 PM
 */
public class SupplierAttributeForm {
    private String attributeId;
    private String attributeName;
    private String attributeDescription;
    private CommonsMultipartFile iconFile;

    public String getAttributeDescription() {
        return attributeDescription;
    }
    public void setAttributeDescription(String attributeDescription) {
        this.attributeDescription = attributeDescription;
    }

    public String getAttributeName() {
        return attributeName;
    }
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public CommonsMultipartFile getIconFile() {
        return iconFile;
    }
    public void setIconFile(CommonsMultipartFile iconFile) {
        this.iconFile = iconFile;
    }

  public String getAttributeId() {
    return attributeId;
  }

  public void setAttributeId(String attributeId) {
    this.attributeId = attributeId;
  }
}
