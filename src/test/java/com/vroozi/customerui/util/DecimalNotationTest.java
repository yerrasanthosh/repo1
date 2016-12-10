package com.vroozi.customerui.util;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static com.vroozi.customerui.util.DecimalNotation.convertDecimalNotation;
import static com.vroozi.customerui.util.DecimalNotation.convertTieredPriceingDecimalNotation;


public class DecimalNotationTest {

  @Test
  public  void convertDecimalNotationCaseDoubleAmount() {
    
    //Given
    final double amount=12345678.10;
    final Integer type=1;
    
    //When
    final String result = convertDecimalNotation(amount, type);
    
    //Then
    assertThat(result, is(not(nullValue())));  
    
  }
  
  @Test
  public  void convertDecimalNotationCaseAmountZero() {
    
    //Given
    final double amount=0;
    final Integer type=1;
    
    //When
    final String result = convertDecimalNotation(amount, type);
    
    //Then
    assertThat(result.isEmpty(), is(true));
    
    
  }
  
  @Test
  public  void convertDecimalNotationCaseTypeNull() {
    
    //Given
    final double amount=0;
    final Integer type=null;
    
    //When
    final String result = convertDecimalNotation(amount, type);
    
    //Then
    assertThat(result.isEmpty(), is(true));
    
    
  }
  
  @Test
  public  void convertTieredPriceingDecimalNotationCaseStringTiredPrice() {
    
    //Given
    final String tieredPrice="60.99 [30-50]; 45.99 [51-200]; 41.99 [201+]";
    final Integer type=1;
    
    //When
    final String result = convertTieredPriceingDecimalNotation(tieredPrice, type);
    
    //Then
    assertThat(result, is(not(nullValue())));
    
    
  }
  
  @Test
  public  void convertTieredPriceingDecimalNotationCaseNullTiredPrice() {
    
    //Given
    final String tieredPrice=null;
    final Integer type=1;
    
    //When
    final String result = convertTieredPriceingDecimalNotation(tieredPrice, type);
    
    //Then
    assertThat(result.isEmpty(), is(true));
    
    
  }
}
