package com.vroozi.customerui.company.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanySettingsVO {
  private String id;
  private boolean categoryMapping;
  private boolean unitOfMeasure;
  private boolean currencyMapping;
  private boolean notInCatalog;
  private boolean limitRequest;
  private boolean transferCustFieldDescription;
  private int announceId;
  private int messageId;
  private String message;
  private String announcement;
  private String featuredProducts;
  private String shopperWelcome;
  private String faq;
  private String policies;
  private String contactUs;
  private String companyIcon;
  private String noCompanyIcon;
  private String findVendorIcon;
  private String mailingList;
  private String colName;
  private boolean requiredField;
  private boolean itemAdded;
  private boolean priceChange;
  private String percentChanged;
  private String companyCode;
  private String welcomeImage;
  private String type;
  private String freeTextIdentifier;
  private String limitItemIdentifier;
  private String requiredDate;
  private String deliveryDateFrom;
  private String overallValue;
  private boolean poChangeRequestEnabled;
  private boolean downloadAllCatalogs = false;
  private String sharedListMapping;

  private int sharedListExpiryDays;
  private boolean sharedListAutoExpire;
  
  public String getSharedListMapping() {
    return sharedListMapping;
  }

  public void setSharedListMapping(String sharedListMapping) {
    this.sharedListMapping = sharedListMapping;
  }

  public String getCompanyCode() {
    return companyCode;
  }

  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }


  public boolean isCategoryMapping() {
    return categoryMapping;
  }

  public void setCategoryMapping(boolean categoryMapping) {
    this.categoryMapping = categoryMapping;
  }

  public boolean isUnitOfMeasure() {
    return unitOfMeasure;
  }

  public void setUnitOfMeasure(boolean unitOfMeasure) {
    this.unitOfMeasure = unitOfMeasure;
  }

  public boolean isCurrencyMapping() {
    return currencyMapping;
  }

  public void setCurrencyMapping(boolean currencyMapping) {
    this.currencyMapping = currencyMapping;
  }

  public boolean isTransferCustFieldDescription() {
    return transferCustFieldDescription;
  }

  public void setTransferCustFieldDescription(boolean transferCustFieldDescription) {
    this.transferCustFieldDescription = transferCustFieldDescription;
  }

  public String getAnnouncement() {
    return announcement;
  }

  public void setAnnouncement(String announcement) {
    this.announcement = announcement;
  }

  public String getFeaturedProducts() {
    return featuredProducts;
  }

  public void setFeaturedProducts(String featuredProducts) {
    this.featuredProducts = featuredProducts;
  }

  public String getShopperWelcome() {
    return shopperWelcome;
  }

  public void setShopperWelcome(String shopperWelcome) {
    this.shopperWelcome = shopperWelcome;
  }

  public String getFaq() {
    return faq;
  }

  public void setFaq(String faq) {
    this.faq = faq;
  }

  public String getPolicies() {
    return policies;
  }

  public void setPolicies(String policies) {
    this.policies = policies;
  }

  public String getContactUs() {
    return contactUs;
  }

  public void setContactUs(String contactUs) {
    this.contactUs = contactUs;
  }

  public int getAnnounceId() {
    return announceId;
  }

  public void setAnnounceId(int announceId) {
    this.announceId = announceId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCompanyIcon() {
    return companyIcon;
  }

  public void setCompanyIcon(String companyIcon) {
    this.companyIcon = companyIcon;
  }

  public String getNoCompanyIcon() {
    return noCompanyIcon;
  }

  public void setNoCompanyIcon(String noCompanyIcon) {
    this.noCompanyIcon = noCompanyIcon;
  }

  public String getMailingList() {
    return mailingList;
  }

  public void setMailingList(String mailingList) {
    this.mailingList = mailingList;
  }

  public String getColName() {
    return colName;
  }

  public void setColName(String colName) {
    this.colName = colName;
  }

  public boolean isRequiredField() {
    return requiredField;
  }

  public void setRequiredField(boolean requiredField) {
    this.requiredField = requiredField;
  }

  public boolean isItemAdded() {
    return itemAdded;
  }

  public void setItemAdded(boolean itemAdded) {
    this.itemAdded = itemAdded;
  }

  public boolean isPriceChange() {
    return priceChange;
  }

  public void setPriceChange(boolean priceChange) {
    this.priceChange = priceChange;
  }

  public String getPercentChanged() {
    return percentChanged;
  }

  public void setPercentChanged(String percentChanged) {
    this.percentChanged = percentChanged;
  }

  public int getMessageId() {
    return messageId;
  }

  public void setMessageId(int messageId) {
    this.messageId = messageId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getWelcomeImage() {
    return welcomeImage;
  }

  public void setWelcomeImage(String welcomeImage) {
    this.welcomeImage = welcomeImage;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isNotInCatalog() {
    return notInCatalog;
  }

  public void setNotInCatalog(boolean notInCatalog) {
    this.notInCatalog = notInCatalog;
  }

  public boolean isLimitRequest() {
    return limitRequest;
  }

  public void setLimitRequest(boolean limitRequest) {
    this.limitRequest = limitRequest;
  }

  public String getFreeTextIdentifier() {
    return freeTextIdentifier;
  }

  public void setFreeTextIdentifier(String freeTextIdentifier) {
    this.freeTextIdentifier = freeTextIdentifier;
  }

  public String getLimitItemIdentifier() {
    return limitItemIdentifier;
  }

  public void setLimitItemIdentifier(String limitItemIdentifier) {
    this.limitItemIdentifier = limitItemIdentifier;
  }

  public String getRequiredDate() {
    return requiredDate;
  }

  public void setRequiredDate(String requiredDate) {
    this.requiredDate = requiredDate;
  }

  public String getDeliveryDateFrom() {
    return deliveryDateFrom;
  }

  public void setDeliveryDateFrom(String deliveryDateFrom) {
    this.deliveryDateFrom = deliveryDateFrom;
  }

  public String getOverallValue() {
    return overallValue;
  }

  public void setOverallValue(String overallValue) {
    this.overallValue = overallValue;
  }

  public String getFindVendorIcon() {
    return findVendorIcon;
  }

  public void setFindVendorIcon(String findVendorIcon) {
    this.findVendorIcon = findVendorIcon;
  }

  public boolean isPOChangeRequestEnabled() {
    return this.poChangeRequestEnabled;
  }
 
  public void setPOChangeRequestEnabled(boolean poChangeRequestEnabled) {
   this.poChangeRequestEnabled = poChangeRequestEnabled;
  }

  public int getSharedListExpiryDays() {
    return sharedListExpiryDays;
  }

  public void setSharedListExpiryDays(int sharedListExpiryDays) {
    this.sharedListExpiryDays = sharedListExpiryDays;
  }

  public boolean isSharedListAutoExpire() {
    return sharedListAutoExpire;
  }

  public void setSharedListAutoExpire(boolean sharedListAutoExpire) {
    this.sharedListAutoExpire = sharedListAutoExpire;
  }
  
  public boolean isDownloadAllCatalogs() {
      return downloadAllCatalogs;
    }

    public void setDownloadAllCatalogs(boolean downloadAllCatalogs) {
      this.downloadAllCatalogs = downloadAllCatalogs;
    }
}
