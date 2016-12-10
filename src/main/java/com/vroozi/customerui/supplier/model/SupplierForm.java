package com.vroozi.customerui.supplier.model;

/**
 * Provides form submission for Supplier Detail page to create/edit supplier.
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 10/15/12:1:37 PM
 */
public class SupplierForm {
  String uniqueSupplierId;
  String supplierId;
  String companyName;
  String dunsNumber;
  String defaultVendorId;
  String addressLine1;
  String addressLine2;
  String countryRegion;
  String stateCounty;
  String city;
  String zipPostalCode;
  String contactName;
  String contactRole;
  String phoneNumber;
  String faxNumber;
  String emailAddress;
  String language;
  String timezone;
  String decimalNotation;
  String dateFormat;
  String logo;
  String minOrder;
  boolean nonCatalogSupplier;
  boolean singleSourceSupplier;
  boolean includeSupplierCard;
  boolean disableBrowse;
  String currencyCode;
  private int rowsPerPage;

  // PROPERTY: uniqueSupplierId
  public String getUniqueSupplierId() {
      return uniqueSupplierId;
  }
  public void setUniqueSupplierId(String uniqueSupplierId) {
      this.uniqueSupplierId = uniqueSupplierId;
  }

  // PROPERTY: supplierId
  public String getSupplierId() {
      return supplierId;
  }
  public void setSupplierId(String supplierId) {
      this.supplierId = supplierId;
  }

  // PROPERTY: addressLine1
  public String getAddressLine1() {
      return addressLine1;
  }
  public void setAddressLine1(String addressLine1) {
      this.addressLine1 = addressLine1;
  }

  // PROPERTY: addressLine2
  public String getAddressLine2() {
      return addressLine2;
  }
  public void setAddressLine2(String addressLine2) {
      this.addressLine2 = addressLine2;
  }

  // PROPERTY: city
  public String getCity() {
      return city;
  }
  public void setCity(String city) {
      this.city = city;
  }

  // PROPERTY: companyName
  public String getCompanyName() {
      return companyName;
  }
  public void setCompanyName(String companyName) {
      this.companyName = companyName;
  }

  // PROPERTY: contactName
  public String getContactName() {
      return contactName;
  }
  public void setContactName(String contactName) {
      this.contactName = contactName;
  }

  // PROPERTY: contactRole
  public String getContactRole() {
      return contactRole;
  }
  public void setContactRole(String contactRole) {
      this.contactRole = contactRole;
  }

  // PROPERTY: countryRegion
  public String getCountryRegion() {
      return countryRegion;
  }
  public void setCountryRegion(String countryRegion) {
      this.countryRegion = countryRegion;
  }

  // PROPERTY: defaultVendorId
  public String getDefaultVendorId() {
      return defaultVendorId;
  }
  public void setDefaultVendorId(String defaultVendorId) {
      this.defaultVendorId = defaultVendorId;
  }

  // PROPERTY: dunsNumber
  public String getDunsNumber() {
      return dunsNumber;
  }
  public void setDunsNumber(String dunsNumber) {
      this.dunsNumber = dunsNumber;
  }

  // PROPERTY: emailAddress
  public String getEmailAddress() {
      return emailAddress;
  }
  public void setEmailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
  }

  // PROPERTY: faxNumber
  public String getFaxNumber() {
      return faxNumber;
  }
  public void setFaxNumber(String faxNumber) {
      this.faxNumber = faxNumber;
  }

  // PROPERTY: dateFormat
  public String getDateFormat() {
      return dateFormat;
  }
  public void setDateFormat(String dateFormat) {
      this.dateFormat = dateFormat;
  }

  // PROPERTY: decimalNotation
  public String getDecimalNotation() {
      return decimalNotation;
  }
  public void setDecimalNotation(String decimalNotation) {
      this.decimalNotation = decimalNotation;
  }

  // PROPERTY: language
  public String getLanguage() {
      return language;
  }
  public void setLanguage(String language) {
      this.language = language;
  }

  // PROPERTY: timezone
  public String getTimezone() {
      return timezone;
  }
  public void setTimezone(String timezone) {
      this.timezone = timezone;
  }

  // PROPERTY: phoneNumber
  public String getPhoneNumber() {
      return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
  }

  // PROPERTY: stateCounty
  public String getStateCounty() {
      return stateCounty;
  }
  public void setStateCounty(String stateCounty) {
      this.stateCounty = stateCounty;
  }

  // PROPERTY: zipPostalCode
  public String getZipPostalCode() {
      return zipPostalCode;
  }
  public void setZipPostalCode(String zipPostalCode) {
      this.zipPostalCode = zipPostalCode;
  }

  // PROPERTY: logo
  public String getLogo() {
      return logo;
  }
  public void setLogo(String logo) {
      this.logo = logo;
  }
	public String getMinOrder() {
		return minOrder;
	}
	public void setMinOrder(String minOrder) {
		this.minOrder = minOrder;
	}
  public boolean isNonCatalogSupplier() {
      return nonCatalogSupplier;
  }
  public void setNonCatalogSupplier(boolean nonCatalogSupplier) {
      this.nonCatalogSupplier = nonCatalogSupplier;
  }

  public boolean isSingleSourceSupplier() {
    return singleSourceSupplier;
  }

  public void setSingleSourceSupplier(boolean singleSourceSupplier) {
    this.singleSourceSupplier = singleSourceSupplier;
  }

  public boolean isIncludeSupplierCard() {
		return includeSupplierCard;
	}
	public void setIncludeSupplierCard(boolean includeSupplierCard) {
		this.includeSupplierCard = includeSupplierCard;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

  public boolean isDisableBrowse() {
      return disableBrowse;
  }

  public void setDisableBrowse(boolean disableBrowse) {
      this.disableBrowse = disableBrowse;
  }

  public int getRowsPerPage() {
    return rowsPerPage;
  }

  public void setRowsPerPage(int rowsPerPage) {
    this.rowsPerPage = rowsPerPage;
  }
}
