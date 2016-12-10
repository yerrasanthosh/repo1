package com.vroozi.customerui.customerui.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.customfield.controller.CustomFieldUIController;

public class CustomFieldUIControllerTest {
  
    
    CustomFieldUIController customFieldController = new CustomFieldUIController();
  
  
  @Test
  public void testOfFilterSearchedResultWithSearchTextAndListOfCatalogSummaryShouldFilterTheList(){
    
    //Given
    String searchText ="a";
    CatalogSummary cs = new CatalogSummary();
    CatalogSummary cs1 = new CatalogSummary();
    List<CatalogSummary> catalogList = new ArrayList<CatalogSummary>();
    cs.setName("iPhone");
    cs1.setName("android");
    catalogList.add(cs);
    catalogList.add(cs1);
    
    //When
    customFieldController.filterSearchedResult(catalogList, searchText);
    
    //Then
    Assert.assertEquals(catalogList.size(), 1);
  }
  
  @Test
  public void testOfFilterSearchedResultWithListOfCatalogSummaryAndSearchTestNotInListShouldReturnEmptyList(){
    
    //Given
    String searchText ="zzzz";
    CatalogSummary cs = new CatalogSummary();
    CatalogSummary cs1 = new CatalogSummary();
    List<CatalogSummary> catalogList = new ArrayList<CatalogSummary>();
    cs.setName("iPhone");
    cs1.setName("android");
    catalogList.add(cs);
    catalogList.add(cs1);
    
    //When
    customFieldController.filterSearchedResult(catalogList, searchText);
    
    //Then
    Assert.assertEquals(catalogList.size(), 0);
  }
  
  @Test
  public void testOfFilterSearchedResultWithSearchTextNullShouldNotAffectTheList(){
    
    //Given
    String searchText =null;
    CatalogSummary cs = new CatalogSummary();
    CatalogSummary cs1 = new CatalogSummary();
    List<CatalogSummary> catalogList = new ArrayList<CatalogSummary>();
    cs.setName("iPhone");
    cs1.setName("android");
    catalogList.add(cs);
    catalogList.add(cs1);
    
    //When
    customFieldController.filterSearchedResult(catalogList, searchText);
    
    //Then
    Assert.assertEquals(catalogList.size(), 2);
  }
  
  @Test
  public void testOfFilterSearchedResultWithListEmptyShouldNotDoAnything(){
    
    //Given
    String searchText =null;
    List<CatalogSummary> catalogList = new ArrayList<CatalogSummary>();
    
    
    //When
    customFieldController.filterSearchedResult(catalogList, searchText);
    
    //Then
    Assert.assertEquals(catalogList.size(), 0);
  }
}
