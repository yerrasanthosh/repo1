package com.vroozi.customerui.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Parameters implements Serializable {

  private static final long serialVersionUID = 1470590488909557138L;

  private List<Filter> filters = new ArrayList<>();
  
  private List<String> excluded = new ArrayList<String>();

  private Sort sort;

  private Pagination pagination;

  private boolean includeCount = true;
  
  public List<Filter> getFilters() {
    return filters;
  }

  public void addFilter(Filter filter) {
    this.filters.add(filter);
  }
  
  public void addExcluded(String exclude) {
    this.excluded.add(exclude);
  }

  public Sort getSort() {
    return sort;
  }

  public void setSort(Sort sort) {
    this.sort = sort;
  }

  public Pagination getPagination() {
    return pagination;
  }

  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }

  public boolean isIncludeCount() {
    return includeCount;
  }

  public void setIncludeCount(boolean includeCount) {
    this.includeCount = includeCount;
  }

  public List<String> getExcluded() {
    return this.excluded;
  }

}
