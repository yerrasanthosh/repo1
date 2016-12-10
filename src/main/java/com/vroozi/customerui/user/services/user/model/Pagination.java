package com.vroozi.customerui.user.services.user.model;

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

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getCurrentPage() {
    return currentPage;
  }


  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public long getItemCount() {
    return itemCount;
  }

  public void setItemCount(long itemCount) {
    this.itemCount = itemCount;
  }

  public long getPageCount() {
    return (long) Math.ceil((double) itemCount / pageSize);
  }

  public void setPageCount(long pageCount) {
    this.pageCount = pageCount;
  }

}
