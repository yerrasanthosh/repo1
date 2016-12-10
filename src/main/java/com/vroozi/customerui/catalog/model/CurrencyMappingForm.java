package com.vroozi.customerui.catalog.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.Serializable;

public class CurrencyMappingForm implements Serializable {

  private CommonsMultipartFile currencyMappingFile;

  public CommonsMultipartFile getCurrencyMappingFile() {
    return currencyMappingFile;
  }

  public void setCurrencyMappingFile(CommonsMultipartFile currencyMappingFile) {
    this.currencyMappingFile = currencyMappingFile;
  }
}
