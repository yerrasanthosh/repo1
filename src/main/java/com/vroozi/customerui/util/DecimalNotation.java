package com.vroozi.customerui.util;

import java.text.NumberFormat;
import java.util.Locale;

public class DecimalNotation {
  /**
   * <b style="margin-left:7px"> Converts the price to user's desired decimal notation.</b>
   * <p>
   * <i style="color:#428484"> This method always returns amount in string. The basis purpose of this
   * method is to convert the amount of catalog item to user's desired decimal notation.</i>
   * 
   * @param amount {@link Double} The amount to be converted
   * @param type {@link Integer} The user preference of decimal notation
   * @return {@link String}
   * @author <a href="mailto:sajid.akram@vroozi.com">Sajid Akram</a>
   */
  public static String convertDecimalNotation(double amount, Integer type) {

    Locale currentLocale = null;
    String result = "";
    if (type != null && amount > 0) {
      if (type == 1)
        currentLocale = new Locale.Builder().setLanguage("en").setRegion("GB").build();
      else if (type == 2)
        currentLocale = new Locale.Builder().setLanguage("de").setRegion("DK").build();
      else if (type == 3)
        currentLocale = new Locale.Builder().setLanguage("fr").setRegion("CA").build();

      NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
      numberFormatter.setMaximumFractionDigits(4);
      result = numberFormatter.format(amount);
    }
    return result;
  }
  /**
   * <b style="margin-left:7px"> Converts the tiered pricing to user's desired decimal notation.</b>
   * <p>
   * <i style="color:#428484"> This method always returns amount in string. The basis purpose of this
   * method is to convert the multiple amounts in tiered price to user's desired decimal notation.</i>
   * 
   * @param tieredPrice {@link String} The amount to be converted
   * @param type {@link Integer} The user preference of decimal notation
   * @return {@link String}
   * @author <a href="mailto:sajid.akram@vroozi.com">Sajid Akram</a>
   */
 
  public static String convertTieredPriceingDecimalNotation(String  tieredPrice, Integer type) {
    String result="";
   if(tieredPrice!= null && type != null){
    String[] options= tieredPrice.split(";");
    String[] restedOptions= null;
    for (String string : options) {
      restedOptions = string.split("\\[");
      if(result.isEmpty())
        result= convertDecimalNotation(Double.parseDouble(restedOptions[0]), type)+"["+restedOptions[1];
      else
        result+=";"+convertDecimalNotation(Double.parseDouble(restedOptions[0]), type)+"["+restedOptions[1];
    }
   }
    return result;
  }
}
