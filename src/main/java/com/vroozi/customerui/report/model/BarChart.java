package com.vroozi.customerui.report.model;

import java.util.List;

public class BarChart {
  
  private List<String> labels;
  private List<BarChartDataSet> datasets;
  
  public List<String> getLabels() {
    return labels;
  }
  public void setLabels(List<String> labels) {
    this.labels = labels;
  }
  public List<BarChartDataSet> getDatasets() {
    return datasets;
  }
  public void setDatasets(List<BarChartDataSet> datasets) {
    this.datasets = datasets;
  }
  public void addDataset(BarChartDataSet dataset) {
    this.datasets.add(dataset);
  }
  
}
