package com.vroozi.customerui.catalog.model;

import com.vroozi.customerui.util.Consts;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ser.std.ContainerSerializerBase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown=true)
public class CatalogSummary extends CatalogProxy {

    public int version;
    public String creatorName;
    public String supplierName;
    public String activationHour;
    public Date startDate;
    public Date endDate;
    private String clientCatalogId;
    public String updatorName;
 //   private String approvedStatus;
    private String originationMethodDesc;
    private String productDetailView;
    private String httpRequestMethod;
    private List<String> itemIds = new ArrayList<String>();
    private String errorDescription;
    private String catalogType;
    private String formatedCreatedOn;
    private String formatedLastUpdated;
    private String formatedValidFrom;

    public boolean showSubHeader;
    public boolean validationFailed;
    private boolean publishItem;
    private String quoteId;
    private String rfqNumber;
    private Integer timeZoneId;
    private List<Integer> profileList;

    public boolean isValidationFailed() {
        if(this.getFailedRecords()!=null && this.getFailedRecords()>0)
            return true;
        else
            return false;
    }

    public void setValidationFailed(boolean validationFailed) {
        this.validationFailed = validationFailed;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public String getClientCatalogId() {
        return clientCatalogId;
    }

    public void setClientCatalogId(String clientCatalogId) {
        this.clientCatalogId = clientCatalogId;
    }

    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public String getActivationHour() {
        return activationHour;
    }
    public void setActivationHour(String activationHour) {
        this.activationHour = activationHour;
    }

    public String getCreatorName() {
        return creatorName;
    }
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    // Dependent properties
    public String getCatalogNameNoSpace(){
        return getName().trim();
    }

    public boolean isApproved(){
        //return (getCatalogStateId() == 2 || getCatalogStateId() == 5);
        if(Consts.CATALOG_STATE_APPROVE.equalsIgnoreCase(getCatalogStatus() )
            || Consts.CATALOG_STATE_APPROVING.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_APPROVED.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_APPROVE_ERROR.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_UNPUBLISH.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_UNPUBLISHING.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_UNPUBLISHED.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_UNPUBLISH_ERROR.equalsIgnoreCase(getCatalogStatus() )){
            return true;
        }
        return false;

    }

    public boolean isPublished(){
        //return (getCatalogStateId() == 4);
        if(Consts.CATALOG_STATE_PUBLISH.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_PUBLISHING.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_PUBLISHED.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_PUBLISH_ERROR.equalsIgnoreCase(getCatalogStatus() )){
            return true;
        }
        return false;
    }

    // Catalog is saved, but it's waiting for approval
    public boolean isPending(){
        //return (getCatalogStateId() == 1 || getCatalogStateId() == 5);
        if(Consts.CATALOG_STATE_NEW.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_NEW_PROCESSING.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_NEW_PROCESSED.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_NEW_ERROR.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_REJECT.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_REJECTING.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_REJECTED.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_REJECT_ERROR.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_UPDATED_PROCESSING.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_UPDATED.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_DEACTIVATE.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_DEACTIVATING.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_DEACTIVATED.equalsIgnoreCase(getCatalogStatus() )
                || Consts.CATALOG_STATE_DEACTIVATE_ERROR.equalsIgnoreCase(getCatalogStatus() )

                ){

            return true;
        }
       return false;
    }

    public int getNumberOfItem(){
        // return getInputRecords() + getOutputRecords() + getFailedRecords();
        return getOutputRecords()!=null?getOutputRecords():0;
    }
    public String getApprovedStatus(){
    	
    	
        if(Consts.CATALOG_STATE_NEW.equalsIgnoreCase(getCatalogStatus())){
            return "Pending";
        } else if(Consts.CATALOG_STATE_NEW_PROCESSING.equalsIgnoreCase(getCatalogStatus()) ){
            return "Processing";
        } else if(Consts.CATALOG_STATE_NEW_PROCESSED.equalsIgnoreCase(getCatalogStatus())){
        	 if(getRejectedItems()!=null && getRejectedItems() > 0 && getRejectedItems() != getNumberOfItem()){
                 return "Partially Rejected";        	
        	 }
            return "New";
        } else if(Consts.CATALOG_STATE_NEW_ERROR.equalsIgnoreCase(getCatalogStatus())){
            return "Error";
        } else if(Consts.CATALOG_STATE_UPDATED_PROCESSING.equalsIgnoreCase(getCatalogStatus()) ){
            return "Processing";
        } else if(Consts.CATALOG_STATE_UPDATED.equalsIgnoreCase(getCatalogStatus()) ){
            if(getRejectedItems()!=null && getRejectedItems() > 0 && getRejectedItems() != getNumberOfItem()){
                return "Partially Rejected";
            }
            return "Updated";
        }
        //Approve
        else if(Consts.CATALOG_STATE_APPROVING.equalsIgnoreCase(getCatalogStatus())){
            return "Approving";//+errorMessage;
        }
        else if(Consts.CATALOG_STATE_APPROVED.equalsIgnoreCase(getCatalogStatus())){
        	 if(getApprovedItems()!=null && getApprovedItems() > 0 && getApprovedItems().intValue() != getNumberOfItem()){
                return "Partially Approved";//+errorMessage;
        	 }
        	 return "Approved";//+errorMessage;
        } else if(Consts.CATALOG_STATE_APPROVE_ERROR.equalsIgnoreCase(getCatalogStatus())){
            return "Error";
        }

        //rejecting
        else if(Consts.CATALOG_STATE_REJECTING.equalsIgnoreCase(getCatalogStatus())){
            return "Rejecting";
        }
        else if(Consts.CATALOG_STATE_REJECTED.equalsIgnoreCase(getCatalogStatus()) && getRejectedItems()!=null && getRejectedItems() > 0 && getRejectedItems() == getNumberOfItem()){
            return "Rejected";
        } else if(Consts.CATALOG_STATE_REJECTED.equalsIgnoreCase(getCatalogStatus()) && getRejectedItems()!=null && getRejectedItems() > 0 && getRejectedItems() != getNumberOfItem()){
            return "Partially Rejected";
        } else if(Consts.CATALOG_STATE_REJECT_ERROR.equalsIgnoreCase(getCatalogStatus())){
            return "Error";
        }
        //deactivating
        else if(Consts.CATALOG_STATE_DEACTIVATING.equalsIgnoreCase(getCatalogStatus())){
            return "Disapproving";                       }
        else if(Consts.CATALOG_STATE_DEACTIVATED.equalsIgnoreCase(getCatalogStatus())){
        
        	return "Disapproved";
        } else if(Consts.CATALOG_STATE_DEACTIVATE_ERROR.equalsIgnoreCase(getCatalogStatus())){
            return "Error";
        }

        //publishing
         else if(Consts.CATALOG_STATE_PUBLISHED.equalsIgnoreCase(getCatalogStatus())){
            if( !getActive()){
                return "Off-Line";
            }else if(((this.getRejectedItems()!=null && this.getRejectedItems()>0) || (getPendingItems()!=null && this.getPendingItems()>0)) || (getPublishedItems()!=null && this.getPublishedItems()<getNumberOfItem())){
        		 return "Partially Published";
        	}else{
        		 return "Published";
        	}
        }
        else if(Consts.CATALOG_STATE_PUBLISH_ERROR.equalsIgnoreCase(getCatalogStatus())){
            return "Error";
        }

        //un-publishing
        else if(Consts.CATALOG_STATE_UNPUBLISHING.equalsIgnoreCase(getCatalogStatus())){
            return "Unpublishing";
        } else if(Consts.CATALOG_STATE_UNPUBLISHED.equalsIgnoreCase(getCatalogStatus())){
            return "Unpublished";
        } else if(Consts.CATALOG_STATE_UNPUBLISH_ERROR.equalsIgnoreCase(getCatalogStatus())){
            return "Error";
        }else if(Consts.CATALOG_STATE_PUBLISHING.equalsIgnoreCase(getCatalogStatus())){
        	return "Publishing";
        } else {
            return "NA";
        }
    }
    public String getActiveStatus() {
        return getActive()?"Active":"Not Active";
    }

    public void setOriginationMethod() {
        if(this.getCatalogOrigin()==null || "".equals(this.getCatalogOrigin())){
            originationMethodDesc = "NA";
        }else if("1".equals(this.getCatalogOrigin())){
            originationMethodDesc = "Direct Upload Buyer";
        }else if("2".equals(this.getCatalogOrigin())){
            originationMethodDesc = "Direct Upload Supplier";
        }else if("3".equals(this.getCatalogOrigin())){
            originationMethodDesc = "Secure FTP";
        }else if("4".equals(this.getCatalogOrigin())){
            originationMethodDesc = "smartCrawl";
        } else if("5".equals(this.getCatalogOrigin())){
            originationMethodDesc = "WebAPI";
        }
        else{
            originationMethodDesc = "NA";
        }
    }

    //      1	External Catalog
//        2	Internal Catalog
//        4	Punch Out
//        3	Quote
    public String getOriginationMethodDesc() {
        setOriginationMethod();
        return originationMethodDesc;
    }

    public void setOriginationMethodDesc(String originationMethodDesc) {
        this.originationMethodDesc = originationMethodDesc;
    }



    public boolean isShowSubHeader(){
        if(this.getFailedRecords()!=null&&this.getFailedRecords()>0){
            showSubHeader = true;
        }else if(Consts.CATALOG_STATE_PUBLISHED.equalsIgnoreCase(getCatalogStatus()) && this.getActive()){
        	if(((this.getRejectedItems()!=null && this.getRejectedItems()>0) || (getPendingItems()!=null && this.getPendingItems()>0)) || (getPublishedItems()!=null && this.getPublishedItems()<getNumberOfItem())){
        		showSubHeader = true;
        	}else{
        		showSubHeader = false;
        	}
        }else if(Consts.CATALOG_STATE_UNPUBLISHED.equalsIgnoreCase(getCatalogStatus()) && this.getActive()){
            showSubHeader = false;
        }else if(Consts.CATALOG_STATE_APPROVED.equalsIgnoreCase(getCatalogStatus())){
        	if((this.getRejectedItems()!=null && this.getRejectedItems()>0) || (getPendingItems()!=null && this.getPendingItems()>0)){
        		showSubHeader = true;
        	}else{
        		showSubHeader = false;
        	}
        }else if(Consts.CATALOG_STATE_REJECTED.equalsIgnoreCase(getCatalogStatus())){
        	if((this.getApprovedItems()!=null && this.getApprovedItems()>0) || (getPendingItems()!=null && this.getPendingItems()>0)){
        		showSubHeader = true;
        	}else{
        		showSubHeader = false;
        	}
        
        } else {
            showSubHeader=true;
        }
        return showSubHeader;
    }
    public void setShowSubHeader(boolean ss){
        showSubHeader = ss;

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

    public List<String> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<String> itemIds) {
        this.itemIds = itemIds;
    }

    public String getCreatedOnGMTTime() {
        if (getCreatedOn() == null) return "";

        return getCreatedOn() .toGMTString();
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String  getCatalogType() {
        switch (this.getCatalogTypeId()) {
            case 0:
                catalogType=  "External Catalog";
                break;
            case 1:
                catalogType= "External Catalog";
                break;
            case 2:
                catalogType=  "Internal Catalog";
                break;
            case 3:
                catalogType=  "Punch Out";
                break;
            case 4:
                catalogType=  "Quote";
                break;
        }

        return catalogType;
    }

    public void  setCatalogType(String catalogType) {
        this.catalogType=catalogType;
    }

    public String getFormatedCreatedOn() {
        return formatedCreatedOn;
    }

    public void setFormatedCreatedOn(String formatedCreatedOn) {
        this.formatedCreatedOn = formatedCreatedOn;
    }

    public String getFormatedLastUpdated() {
        return formatedLastUpdated;
    }

    public void setFormatedLastUpdated(String formatedLastUpdated) {
        this.formatedLastUpdated = formatedLastUpdated;
    }

    public String getFormatedValidFrom() {
        return formatedValidFrom;
    }

    public void setFormatedValidFrom(String formatedValidFrom) {
        this.formatedValidFrom = formatedValidFrom;
    }

    public boolean isPublishItem() {
        return publishItem;
    }

    public void setPublishItem(boolean publishItem) {
        this.publishItem = publishItem;
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
    
    public Integer getTimeZoneId() {
      return this.timeZoneId;
    }
    
    public void setTimeZoneId(Integer timeZoneId) {
      this.timeZoneId = timeZoneId;
    }
    
    public List<Integer> getProfileList() {
      return this.profileList;
    }
    
    public void setProfileList(List<Integer> profileList) {
      this.profileList = profileList;
    }
}
