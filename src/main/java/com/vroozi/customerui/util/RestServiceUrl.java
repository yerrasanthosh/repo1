package com.vroozi.customerui.util;

import com.vroozi.customerui.config.AppConfig;

public class RestServiceUrl {

  AppConfig appConfig;



  public RestServiceUrl(AppConfig appConfig) {
    this.appConfig = appConfig;
  }

  public String getFindContentShareMappingByUsernameURI(){return appConfig.findContentShareMappingByUsernameURI;};

  public String getfindContentShareMappingsURI() {
    return appConfig.findContentShareMappingsURI;
  }

  public String getContentShareMappingsURI() {
    return appConfig.contentShareMappingsURI;
  }

  public String getUpdateContentShareMappingURI() {
    return appConfig.updateContentShareMappingURI;
  }

  public String getdeleteContentShareMappingsURI() {
    return appConfig.deleteContentShareMappingsURI;
  }

  public String getUpdateUserSpecificKeysURI() {
    return appConfig.updateUserSpecificKeysURI;
  }

  public String getUserByUserNameURI() {
    return appConfig.userByUserNameURI;
  }

  public String getFindMaterialGroupURI() {
    return appConfig.materialGroupURI/*+appConfig.findMaterialGroupURI*/;
  }

  public String getFindMaterialGroupMappingPaginationURI() {
    return appConfig.materialGroupURI + appConfig.findMaterialGroupMappingPaginationURI;
  }

  public String getCountMaterialGroupMappingsURI() {
    return appConfig.materialGroupURI + appConfig.countMaterialGroupMappingsURI;
  }

  public String getUploadMaterialGroupURI() {
    return appConfig.materialGroupURI + appConfig.saveMaterialGroupURI;
  }

  public String getMaterialGroupStatusURI() {
    return appConfig.materialGroupURI + appConfig.materialGroupMappingStatusPath;
  }

  public String getCatalogServiceBaseURI() {
    return appConfig.catalogServiceURI;
  }

  public String getUpdateItemStatusURI() {
    return appConfig.updateItemStatusServicePath;
  }

  public String getItemURI() {
    return appConfig.catalogItemsPath;
  }

  public String getItemAttachmentURI() {
    return appConfig.attachmentsURI;
  }

  public String getItemImageURI() {
    return appConfig.imagesURI;
  }


  public String getExportedFileURI() {
    return getCatalogServiceBaseURI() + appConfig.exportedCatalogPath;
  }

  public String getExportCatalogURI() {
    return getCatalogServiceBaseURI() + appConfig.exportCatalogPath;
  }

  public String getCatalogChangeHistoryURI() {
    return getCatalogServiceBaseURI() + appConfig.catalogChangeHistoryPath;
  }

  public String getCatalogListURI() {
    return getCatalogServiceBaseURI() + appConfig.catalogListServicePath;
  }

  public String catalogProfileURI() {
    return userDataServiceURI() + appConfig.catalogProfilePath;
  }

  public String catalogPropertyURI() {
    return getCatalogServiceBaseURI() + appConfig.catalogPropertyPath;
  }

  public String getCatalogGroupWiseCountsURI() {
    return appConfig.catalogGroupWiseCountsURI;
  }

  public String getCatalogErroneousCountURI() {
    return appConfig.catalogErroneousCountURI;
  }

  public String catalogUserURI() {
    return userDataServiceURI() + appConfig.catalogUserPath;
  }

  public String userBatchURI() {
    return userDataServiceURI() + appConfig.userBatchPath;
  }

  public String tempUserURI() {
    return userDataServiceURI() + appConfig.tempUserPath;
  }

  public String updateTempUserURI() {
    return userDataServiceURI() + appConfig.updateTempUser;
  }

  public String userFindURI() {
    return appConfig.findUserURI;
  }

  public String getUserRoleWiseCountsURI() {
    return appConfig.userRoleWiseCountsURI;
  }

  public String getSupplierStatusWiseCountsURI() {
    return appConfig.supplierStatusWiseCountsURI;
  }

  public String accessControlListURI() {
    return appConfig.accessControlListURI;
  }

  public String userDataServiceURI() {
    return appConfig.userDataServiceURI;
  }

  public String getSearchApiBaseURI() {
    return appConfig.searchApiBaseURI;
  }

  public String userSessionDataURI() {
    return appConfig.loginDataServiceURI + appConfig.userSessionDataPath;
  }

  public String loginDataServiceURI() {
    return appConfig.loginDataServiceURI + appConfig.authenticatePath;
  }

  public String forgotPasswordURI() {
    return appConfig.loginDataServiceURI + appConfig.forgotPasswordPath;
  }

  public String getSupplierPathURI() {
    return userDataServiceURI() + appConfig.supplierCompanyURI;
  }


  public String getCatalogServiceSearchPath() {
    return getCatalogServiceBaseURI() + appConfig.catalogServiceSearchPath;
  }

  public String getCatalogServiceSearchByUnitIdPath() {
    return getCatalogServiceSearchPath() + appConfig.searchByUnitIdPath;
  }

  public String geSearchServiceSearchByUnitIdPath() {
    return getSearchApiBaseURI() + appConfig.searchByUnitIdPath;
  }

  public String getNotificationMessagePath() {
    return appConfig.notificationApiBaseURI + appConfig.notificationPath;
  }

  public String getMaterialGroupErrorStatusURI() {
    return appConfig.materialGroupURI + appConfig.materialGroupMappingErrorStatusPath;
  }

  public String getUploadSupplierMappingURI() {
    return appConfig.supplierMappingURI + appConfig.uploadSupplierMapping;
  }

  public String getUploadSupplierBulkURI() {
    return appConfig.supplierBulkUploadURI + appConfig.supplierBulkUpload;
  }

  public String getSupplierBulkUploadPaginationURI() {
    return appConfig.supplierBulkUploadURI + appConfig.findSupplierBulkUploadPaginationURI;
  }

  public String getSupplierMappingPaginationURI() {
    return appConfig.supplierMappingURI + appConfig.findSupplierMappingPaginationURI;
  }

  public String getDownloadSupplierMappingURI() {
    return appConfig.downloadSupplierMappingURI;
  }

  public String getDownloadSupplierBulkUploadURI() {
    return appConfig.downloadSupplierBulkUploadURI;
  }

  public String getCountSupplierMappingsURI() {
    return appConfig.supplierMappingURI + appConfig.countSupplierMappingsURI;
  }

  public String getCountSupplierBulkUploadURI() {
    return appConfig.supplierBulkUploadURI + appConfig.countSupplierBulkUploadURI;
  }

  public String getSupplierStatusURI() {
    return appConfig.supplierMappingURI + appConfig.supplierMappingStatusPath;
  }

  public String getSupplierBulkUploadStatusURI() {
    return appConfig.supplierBulkUploadURI + appConfig.supplierBulkUploadStatusPath;
  }

  public String getVerifyMappingExistsURI() {
    return appConfig.supplierMappingURI + appConfig.checkMappingURI;
  }

  public String getAddNewMappingURI() {
    return appConfig.supplierMappingURI + appConfig.addMappingURI;
  }

  public String getUpdateMappingURI() {
    return appConfig.supplierMappingURI + appConfig.updateMappingURI;
  }


  public String getDeleteSupplierMappingURI() {
    return appConfig.supplierMappingURI + appConfig.deleteSupplierMappingURI;
  }

  public String profileGroupURI() {
    return userDataServiceURI() + appConfig.profileGroupPath;
  }

  public String profileGroupPage() {
    return userDataServiceURI() + appConfig.profileGroupPath;
  }


  public String profileGroupMappingStatusURI() {
    return userDataServiceURI() + appConfig.profileGroupPath + appConfig.profileGroupMappingStatus;
  }

  public String profileGroupMappingUploadURI() {
    return userDataServiceURI() + appConfig.profileGroupPath + appConfig.profileGroupMappingUpload;
  }

  public String uomMappingUploadURI() {
    return userDataServiceURI() + appConfig.uomMappingPath + appConfig.uomMappingUpload;
  }

  public String contentAccessURI() {
    return userDataServiceURI() + appConfig.contentAccessURI;
  }

  public String deleteContentAccessURI() {
    return userDataServiceURI() + appConfig.deleteContentAccessURI;
  }

  public String downloadContentAccessURI() {
    return userDataServiceURI() + appConfig.downloadContentAccessURI;
  }

  public String profileGroupMappingPageURI() {
    return userDataServiceURI() + appConfig.profileGroupPath + appConfig.profileGroupMappingPage;
  }

  public String uomMappingPageURI() {
    return userDataServiceURI() + appConfig.uomMappingPath + appConfig.uomMappingPage;
  }

  public String profileGroupMappingCountURI() {
    return userDataServiceURI() + appConfig.profileGroupPath + appConfig.profileGroupMappingCount;
  }

  public String uomMappingCountURI() {
    return userDataServiceURI() + appConfig.uomMappingPath + appConfig.uomMappingCount;
  }

  public String getUserProfilesPath() {
    return userDataServiceURI() + appConfig.userProfiles;
  }

  public String uomMappingStatusURI() {
    return userDataServiceURI() + appConfig.uomMappingPath + appConfig.uomMappingStatus;
  }

  public String downloadUOMMappingURI() {
    return userDataServiceURI() + appConfig.uomMappingPath + appConfig.downloadUOMMappingURI;
  }

  public String getReportingApiURI() {
    return appConfig.reportingApiURI;
  }

  public String getSpendByMonthURI() {
    return appConfig.spendByMonth;
  }

  public String getSpendByCategoryURI() {
    return appConfig.spendByCategory;
  }
  
  public String getSpendTotalByCategoryURI() {
    return appConfig.spendTotalByCategoryURI;
  }

  public String getCategorySpendingURI() {
    return appConfig.categorySpendingURI;
  }
  
  public String getSpendTotalByShopperAndCategoryURI() {
    return appConfig.spendTotalByShopperAndCategoryURI;
  }

  public String getSpendByCatalogTypeURI() {
    return appConfig.spendByCatalogType;
  }

  public String getSpendBySupplierURI() {
    return appConfig.spendBySupplier;
  }

  public String downloadReports() {
    return userDataServiceURI();
  }

  public String currencyMappingUploadURI() {
    return userDataServiceURI() + appConfig.currencyMappingPath + appConfig.currencyMappingUpload;
  }

  public String currencyMappingPageURI() {
    return userDataServiceURI() + appConfig.currencyMappingPath + appConfig.currencyMappingPage;
  }

  public String currencyMappingCountURI() {
    return userDataServiceURI() + appConfig.currencyMappingPath + appConfig.currencyMappingCount;
  }

  public String currencyMappingStatusURI() {
    return userDataServiceURI() + appConfig.currencyMappingPath + appConfig.currencyMappingStatus;
  }

  public String downloadCurrencyMappingURI() {
    return userDataServiceURI() + appConfig.currencyMappingPath
        + appConfig.downloadCurrencyMappingURI;
  }

  public String downloadAllCatalogs() {
    return getCatalogServiceBaseURI()+ "/catalogs/download-all-catalogs";
  }
}
