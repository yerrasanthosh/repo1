package com.vroozi.customerui.query;

public class Pagination {

  private int pageSize;
  private int currentPage;
  private long itemCount;
  private long pageCount;

  public Pagination() {}

  public Pagination(int pageSize, int currentPage) {
    this.pageSize = pageSize;
    this.currentPage = currentPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public int getCurrentPage() {
    return currentPage;
  }
  
  public long getItemCount() {
    return itemCount;
  }

  public void setItemCount(long itemCount) {
    this.itemCount = itemCount;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public long getPageCount() {
    return (long) Math.ceil((double) itemCount / pageSize);
  }

  public void setPageCount(long pageCount) {
    this.pageCount = pageCount;
  }

}
