package com.vroozi.customerui.search.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 12/28/12
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResults {
    private List<SearchResultVO> productOffers;
    private List<SupplierCompany> suppliers;
    private List<CustomFieldPostFilter> customFieldPostFilters;
    private List<CompanyAttribute> companyAttributes;
    private List<ItemAttribute> itemAttributes;
    private List<RatingPostFilter> ratingPostFilter;
    private long hitsFound;

    public List<SearchResultVO> getProductOffers() {
        return productOffers;
    }

    public void setProductOffers(List<SearchResultVO> productOffers) {
        this.productOffers = productOffers;
    }

    public long getHitsFound() {
        return hitsFound;
    }

    public void setHitsFound(long hitsFound) {
        this.hitsFound = hitsFound;
    }

    public List<SupplierCompany> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierCompany> suppliers) {
        this.suppliers = suppliers;
    }

    public List<CustomFieldPostFilter> getCustomFieldPostFilters() {
        return customFieldPostFilters;
    }

    public void setCustomFieldPostFilters(List<CustomFieldPostFilter> customFieldPostFilters) {
        this.customFieldPostFilters = customFieldPostFilters;
    }

    public List<CompanyAttribute> getCompanyAttributes() {
        return companyAttributes;
    }

    public void setCompanyAttributes(List<CompanyAttribute> companyAttributes) {
        this.companyAttributes = companyAttributes;
    }

    public List<ItemAttribute> getItemAttributes() {
        return itemAttributes;
    }

    public void setItemAttributes(List<ItemAttribute> itemAttributes) {
        this.itemAttributes = itemAttributes;
    }

    public List<RatingPostFilter> getRatingPostFilter() {
        return ratingPostFilter;
    }

    public void setRatingPostFilter(List<RatingPostFilter> ratingPostFilter) {
        this.ratingPostFilter = ratingPostFilter;
    }
}
