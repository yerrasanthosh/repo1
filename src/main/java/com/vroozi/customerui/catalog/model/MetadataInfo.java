package com.vroozi.customerui.catalog.model;

/**
 * User: SURYA MANDADAPU
 * Date: 11/9/12
 * Time: 12:02 PM
 */
public class MetadataInfo {
    private Long pageStartIndex;
    private Long pageSize;
    private Long totalRecords;

    public Long getPageStartIndex() {
        return pageStartIndex;
    }

    public void setPageStartIndex(Long pageStartIndex) {
        this.pageStartIndex = pageStartIndex;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
