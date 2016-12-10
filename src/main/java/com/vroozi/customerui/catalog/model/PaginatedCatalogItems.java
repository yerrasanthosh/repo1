package com.vroozi.customerui.catalog.model;

import com.vroozi.customerui.catalogItem.model.Item;
import java.util.List;
/**
 * User: SURYA MANDADAPU
 * Date: 11/7/12
 * Time: 10:13 PM
 */
public class PaginatedCatalogItems {
    private int pageSize;
    private int totalNumberOfPages;
    private long totalNumberOfItems;
    private String filterQuery;
    private List<Item> catalogItems;

    private long recordStart;
    private long recordEnd;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setTotalNumberOfItems(Long totalNumberOfItems) {
        this.totalNumberOfItems = totalNumberOfItems;
    }

    public long getTotalNumberOfItems() {
        return totalNumberOfItems;
    }

    public void setTotalNumberOfPages(int totalNumberOfPages) {
        this.totalNumberOfPages = totalNumberOfPages;
    }

    public int getTotalNumberOfPages() {
        return totalNumberOfPages;
    }

    public void setFilterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
    }

    public String getFilterQuery() {
        return filterQuery;
    }

    public void setCatalogItems(List<Item> catalogItems) {
        this.catalogItems = catalogItems;
    }

    public List<Item> getCatalogItems() {
        return catalogItems;
    }

    public void setRecordStart(long recordStart) {
        this.recordStart = recordStart;
    }

    public long getRecordStart() {
        return recordStart;
    }

    public void setRecordEnd(long recordEnd) {
        this.recordEnd = recordEnd;
    }

    public long getRecordEnd() {
        return recordEnd;
    }
}
