package com.vroozi.customerui.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BarChartDataSet {

  private String label;
  private String fillColor;
  private String highlightFill;
  private List<BigDecimal> data = new ArrayList<>();
  
  public String getLabel() {
    return label;
  }
  public void setLabel(String label) {
    this.label = label;
  }
  public String getFillColor() {
    return fillColor;
  }
  public void setFillColor(String fillColor) {
    this.fillColor = fillColor;
  }
  public String getHighlightFill() {
    return highlightFill;
  }
  public void setHighlightFill(String highlightFill) {
    this.highlightFill = highlightFill;
  }
  public List<BigDecimal> getData() {
    return data;
  }
  public void setData(List<BigDecimal> data) {
    this.data = data;
  }
  
  public void addIntoData(BigDecimal value) {
    data.add(value);
  }
}
