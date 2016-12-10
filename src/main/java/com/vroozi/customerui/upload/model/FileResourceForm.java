package com.vroozi.customerui.upload.model;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * User: SURYA MANDADAPU
 * Date: 10/17/12
 * Time: 11:40 AM
 */
public class FileResourceForm {
    private CommonsMultipartFile attachmentFile;
    private CommonsMultipartFile imageFile;
    public String catalogId;
    public String catalogItemId;
    public String unitId;
    public String fileTitle;
    public String fileTag;
    public String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public String getFileTag() {
        return fileTag;
    }

    public void setFileTag(String fileTag) {
        this.fileTag = fileTag;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public CommonsMultipartFile getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(CommonsMultipartFile attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    public CommonsMultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(CommonsMultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogItemId() {
        return catalogItemId;
    }

    public void setCatalogItemId(String catalogItemId) {
        this.catalogItemId = catalogItemId;
    }
}
