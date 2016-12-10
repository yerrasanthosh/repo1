package com.vroozi.customerui.report.model;
import java.util.ArrayList;
import java.util.List;

public class MonthlySpend {

  private List<String> labels;
  private List<MonthlySpendData> datasets = new ArrayList<>();
  
  public List<String> getLabels() {
    return labels;
  }
  public void setLabels(List<String> labels) {
    this.labels = labels;
  }
  public List<MonthlySpendData> getDatasets() {
    return datasets;
  }
  public void setDatasets(List<MonthlySpendData> datasets) {
    this.datasets = datasets;
  }

  public void addDataset(MonthlySpendData dataset) {
    this.datasets.add(dataset);
  }
}
