package com.vroozi.customerui.company.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanySettings {

  private String id;
  private String companyName;
  private String userName;
  private String password;
  private boolean showReportsMenu;
  private boolean categoryMapping;
  private boolean unitOfMeasure;
  private boolean notInCatalog;
  private boolean limitRequest =true;
  private boolean currency;
  private boolean transferCustFieldDescription;
  private String unitId;
  private String policy;
  private String faq;
  private String contactUs;
  private List<HelpContent> helpTabs;
  private String companyIcon;
  private String noCompanyIcon;
  private String findVendorIcon;
  // private String colName;
  // private String colValue;
  private boolean requiredField;
  private boolean itemAdded;
  private boolean priceChange;

  private boolean appliedOnMaster;
  private boolean appliedOnAdmin;
  private boolean appliedOnSupplier;

  private String percentChanged;
  private String companyCode;
  private String welcomeImage;
  private String faceText;
  private String defaultBuyerGroupId;
  private boolean welcomeWidgetText;
  private SupplierCardOrder supplierCardOrder;

  private boolean companyAttributesConsumed;

  private Map<String, String> notInCatalogFieldMapping;

  private boolean docuSignEnabled;
  private boolean companyDefinePrScheme;
  private double purchaseRequestCompanyScheme;

  private String defaultApproverId;
  private String defaultPurchasingOrganizationId;

  private String defaultAddressId;
  private String defaultShippingAddressId;

  private String workflowStyle;
  private String preferredStatsBar;

  private String defaultTaxCode;

  private int leadTime;

  private boolean externalWorkflow;

  private String webTitle;
  private String favicon;

  private boolean disableGLAccount = false;
  private boolean enableBuyerGroups = true;

  private boolean attachmentRequired = true;
  private int sessionTimeout;
  private int sessionTimeoutWarning;

  private Boolean downloadAllCatalogs;
  private boolean poChangeRequestEnabled;

  private String sharedListMapping;
  private PurchaseDocumentSettings purchaseDocumentSettings;
  
  public String getSharedListMapping() {
    return sharedListMapping;
  }

  public void setSharedListMapping(String sharedListMapping) {
    this.sharedListMapping = sharedListMapping;
  }

  private int sharedListExpiryDays;
  private boolean sharedListAutoExpire;

  public boolean isAttachmentRequired() {
    return attachmentRequired;
  }

  public void setAttachmentRequired(boolean attachmentRequired) {
    this.attachmentRequired = attachmentRequired;
  }

  public int getLeadTime() {
    return leadTime;
  }

  public void setLeadTime(int leadTime) {
    this.leadTime = leadTime;
  }

  public String getDefaultApproverId() {
    return defaultApproverId;
  }

  public void setDefaultApproverId(String defaultApproverId) {
    this.defaultApproverId = defaultApproverId;
  }

  public String getWorkflowStyle() {
    return workflowStyle;
  }

  public void setWorkflowStyle(String workflowStyle) {
    this.workflowStyle = workflowStyle;
  }

  public boolean isAppliedOnMaster() {
    return appliedOnMaster;
  }

  public void setAppliedOnMaster(boolean appliedOnMaster) {
    this.appliedOnMaster = appliedOnMaster;
  }

  public boolean isAppliedOnAdmin() {
    return appliedOnAdmin;
  }

  public void setAppliedOnAdmin(boolean appliedOnAdmin) {
    this.appliedOnAdmin = appliedOnAdmin;
  }

  public boolean isAppliedOnSupplier() {
    return appliedOnSupplier;
  }

  public void setAppliedOnSupplier(boolean appliedOnSupplier) {
    this.appliedOnSupplier = appliedOnSupplier;
  }

  public String getCompanyCode() {
    return companyCode;
  }

  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
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

  public boolean isCurrency() {
    return currency;
  }

  public void setCurrency(boolean currency) {
    this.currency = currency;
  }

  public boolean isTransferCustFieldDescription() {
    return transferCustFieldDescription;
  }

  public void setTransferCustFieldDescription(boolean transferCustFieldDescription) {
    this.transferCustFieldDescription = transferCustFieldDescription;
  }

  public PurchaseDocumentSettings getPurchaseDocumentSettings() {
    return purchaseDocumentSettings;
  }

  public void setPurchaseDocumentSettings(PurchaseDocumentSettings purchaseDocumentSettings) {
    this.purchaseDocumentSettings = purchaseDocumentSettings;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getPolicy() {
    return policy;
  }

  public void setPolicy(String policy) {
    this.policy = policy;
  }

  public String getFaq() {
    return faq;
  }

  public void setFaq(String faq) {
    this.faq = faq;
  }

  public String getContactUs() {
    return contactUs;
  }

  public void setContactUs(String contactUs) {
    this.contactUs = contactUs;
  }


  public List<HelpContent> getHelpTabs() {
    return helpTabs;
  }

  public void setHelpTabs(List<HelpContent> helpTabs) {
    this.helpTabs = helpTabs;
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

  // public String getColName() {
  // return colName;
  // }
  //
  // public void setColName(String colName) {
  // this.colName = colName;
  // }
  //
  // public String getColValue() {
  // return colValue;
  // }
  //
  // public void setColValue(String colValue) {
  // this.colValue = colValue;
  // }

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

  public boolean isCompanyAttributesConsumed() {
    return companyAttributesConsumed;
  }

  public void setCompanyAttributesConsumed(boolean companyAttributesConsumed) {
    this.companyAttributesConsumed = companyAttributesConsumed;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getWelcomeImage() {
    return welcomeImage;
  }

  public void setWelcomeImage(String welcomeImage) {
    this.welcomeImage = welcomeImage;
  }

  public String getFaceText() {
    return faceText;
  }

  public void setFaceText(String faceText) {
    this.faceText = faceText;
  }

  public String getDefaultBuyerGroupId() {
    return defaultBuyerGroupId;
  }

  public void setDefaultBuyerGroupId(String defaultBuyerGroupId) {
    this.defaultBuyerGroupId = defaultBuyerGroupId;
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

  public SupplierCardOrder getSupplierCardOrder() {
    return supplierCardOrder;
  }

  public void setSupplierCardOrder(SupplierCardOrder supplierCardOrder) {
    this.supplierCardOrder = supplierCardOrder;
  }

  public Map<String, String> getNotInCatalogFieldMapping() {
    return notInCatalogFieldMapping;
  }

  public void setNotInCatalogFieldMapping(Map<String, String> notInCatalogFieldMapping) {
    this.notInCatalogFieldMapping = notInCatalogFieldMapping;
  }

  public boolean isDocuSignEnabled() {
    return docuSignEnabled;
  }

  public void setDocuSignEnabled(boolean docuSignEnabled) {
    this.docuSignEnabled = docuSignEnabled;
  }

  public boolean isCompanyDefinePrScheme() {
    return companyDefinePrScheme;
  }

  public void setCompanyDefinePrScheme(boolean companyDefinePrScheme) {
    this.companyDefinePrScheme = companyDefinePrScheme;
  }

  public double getPurchaseRequestCompanyScheme() {
    return purchaseRequestCompanyScheme;
  }

  public void setPurchaseRequestCompanyScheme(double purchaseRequestCompanyScheme) {
    this.purchaseRequestCompanyScheme = purchaseRequestCompanyScheme;
  }

  public String getDefaultTaxCode() {
    return defaultTaxCode;
  }

  public void setDefaultTaxCode(String defaultTaxCode) {
    this.defaultTaxCode = defaultTaxCode;
  }

  public String getFindVendorIcon() {
    return findVendorIcon;
  }

  public void setFindVendorIcon(String findVendorIcon) {
    this.findVendorIcon = findVendorIcon;
  }

  public String getDefaultPurchasingOrganizationId() {
    return defaultPurchasingOrganizationId;
  }

  public void setDefaultPurchasingOrganizationId(String defaultPurchasingOrganizationId) {
    this.defaultPurchasingOrganizationId = defaultPurchasingOrganizationId;
  }

  public String getDefaultAddressId() {
    return defaultAddressId;
  }

  public void setDefaultAddressId(String defaultAddressId) {
    this.defaultAddressId = defaultAddressId;
  }

  public String getDefaultShippingAddressId() {
    return defaultShippingAddressId;
  }

  public void setDefaultShippingAddressId(String defaultShippingAddressId) {
    this.defaultShippingAddressId = defaultShippingAddressId;
  }

  public boolean isExternalWorkflow() {
    return externalWorkflow;
  }

  public void setExternalWorkflow(boolean externalWorkflow) {
    this.externalWorkflow = externalWorkflow;
  }

  public String getWebTitle() {
    return webTitle;
  }

  public void setWebTitle(String webTitle) {
    this.webTitle = webTitle;
  }

  public boolean isDisableGLAccount() {
    return disableGLAccount;
  }

  public void setDisableGLAccount(boolean disableGLAccount) {
    this.disableGLAccount = disableGLAccount;
  }

  public boolean isEnableBuyerGroups() {
    return enableBuyerGroups;
  }

  public void setEnableBuyerGroups(boolean enableBuyerGroups) {
    this.enableBuyerGroups = enableBuyerGroups;
  }

  public String getPreferredStatsBar() {
    return preferredStatsBar;
  }

  public void setPreferredStatsBar(String preferredStatsBar) {
    this.preferredStatsBar = preferredStatsBar;
  }

  public boolean isWelcomeWidgetText() {
    return welcomeWidgetText;
  }

  public void setWelcomeWidgetText(boolean welcomeWidgetText) {
    this.welcomeWidgetText = welcomeWidgetText;
  }

  public String getFavicon() {
    return favicon;
  }

  public void setFavicon(String favicon) {
    this.favicon = favicon;
  }

  public int getSessionTimeout() {
    return sessionTimeout;
  }

  public void setSessionTimeout(int sessionTimeout) {
    this.sessionTimeout = sessionTimeout;
  }

  public int getSessionTimeoutWarning() {
    return sessionTimeoutWarning;
  }

  public void setSessionTimeoutWarning(int sessionTimeoutWarning) {
    this.sessionTimeoutWarning = sessionTimeoutWarning;
  }

  public Boolean getDownloadAllCatalogs() {
    return downloadAllCatalogs;
  }

  public void setDownloadAllCatalogs(Boolean downloadAllCatalogs) {
    this.downloadAllCatalogs = downloadAllCatalogs;
  }

  public boolean isShowReportsMenu() {
    return showReportsMenu;
  }

  public void setShowReportsMenu(boolean showReportsMenu) {
    this.showReportsMenu = showReportsMenu;
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

}
