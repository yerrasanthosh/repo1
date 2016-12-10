package com.vroozi.customerui.upload.model;
import java.io.Serializable;
import java.util.Date;

/**
 * User: SURYA MANDADAPU
 * Date: 10/23/12
 * Time: 3:24 PM
 */
public class ImageAttachmentResource implements Serializable {


    public String fileResourceId;
    public String fileResourceBase;
    public String fileResourceOrig;
    public Date createdDate;
    public String fileImagesOrig;
    public String fileType;
    public String fileName;
    public String fileTag="";
    public String unitId;



    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileTag() {
        return fileTag;
    }

    public void setFileTag(String fileTag) {
        this.fileTag = fileTag;
    }

    public String getFileImagesOrig() {
        return fileImagesOrig;
    }

    public void setFileImagesOrig(String fileImagesOrig) {
        this.fileImagesOrig = fileImagesOrig;
    }

    public String getFileResourceId() {
        return fileResourceId;
    }

    public void setFileResourceId(String fileResourceId) {
        this.fileResourceId = fileResourceId;
    }

    public String getFileResourceBase() {
        return fileResourceBase;
    }

    public void setFileResourceBase(String fileResourceBase) {
        this.fileResourceBase = fileResourceBase;
    }

    public String getFileResourceOrig() {
        return fileResourceOrig;
    }

    public void setFileResourceOrig(String fileResourceOrig) {
        this.fileResourceOrig = fileResourceOrig;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}