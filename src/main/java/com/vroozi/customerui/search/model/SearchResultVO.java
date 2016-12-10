package com.vroozi.customerui.search.model;

import java.io.Serializable;
import java.util.List;



/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 9/24/12
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultVO implements Serializable {
    private String id;
    private String catalogId;
    private String name;
    private String description;
    private double price;
    private String currency;
    private String materialGroup;
    private String image;
    private String vendorMat;
    private String manufactMat;
    private String newItemVendor;
    private String supplierInfo;
    private String supplierName;
    private String vendorName;
    private String priceRange;
    private String quantity;
    private String productId;
    private String itemConfig;
    private String itemPolicy;
    private Boolean configurable;
    private String tieredPricing;
    private String minOrderQty;
    private String orderInterval;
    private List<String> itemAttributesIcons;
    private List<String> companyAttributesIcons;
    private String unit;
    private double sortPrice;
    private String rating;
    private String count;
    
    private String catalogName;

    private String bundleNumber;
    private String bundleQuantity;
    private String newItemExtQuoteId;
    private String newItemExtQuoteItem;
    private String quoteQuantity;
    private String itemSuppress;
    private String itemSuppressPrice;
    private String leadtime;
    
    public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (null!=description && description.length()>100) {
            description = description.substring(0,100) + "...";
        }
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMaterialGroup() {
        return materialGroup;
    }

    public void setMaterialGroup(String materialGroup) {
        this.materialGroup = materialGroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVendorMat() {
        return vendorMat;
    }

    public void setVendorMat(String vendorMat) {
        this.vendorMat = vendorMat;
    }

    public String getManufactMat() {
        return manufactMat;
    }

    public void setManufactMat(String manufactMat) {
        this.manufactMat = manufactMat;
    }

    public String getNewItemVendor() {
        return newItemVendor;
    }

    public void setNewItemVendor(String newItemVendor) {
        this.newItemVendor = newItemVendor;
    }

    public String getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(String supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

	public String getItemConfig() {
		return itemConfig;
	}

	public void setItemConfig(String itemConfig) {
		this.itemConfig = itemConfig;
	}

    public String getItemPolicy() {
        return itemPolicy;
    }

    public void setItemPolicy(String itemPolicy) {
        this.itemPolicy = itemPolicy;
    }

    public Boolean getConfigurable() {
		return configurable;
	}

	public void setConfigurable(Boolean configurable) {
		this.configurable = configurable;
	}

    public List<String> getItemAttributesIcons() {
        return itemAttributesIcons;
    }

    public void setItemAttributesIcons(List<String> itemAttributesIcons) {
        this.itemAttributesIcons = itemAttributesIcons;
    }

    public List<String> getCompanyAttributesIcons() {
        return companyAttributesIcons;
    }

    public void setCompanyAttributesIcons(List<String> companyAttributesIcons) {
        this.companyAttributesIcons = companyAttributesIcons;
    }

    public String getTieredPricing() {
        return tieredPricing;
    }

    public void setTieredPricing(String tieredPricing) {
        this.tieredPricing = tieredPricing;
    }

    public String getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(String minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public String getOrderInterval() {
		return orderInterval;
	}

	public void setOrderInterval(String orderInterval) {
		this.orderInterval = orderInterval;
	}

	public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getSortPrice() {
        return sortPrice;
    }

    public void setSortPrice(double sortPrice) {
        this.sortPrice = sortPrice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getBundleNumber() {
        return bundleNumber;
    }

    public void setBundleNumber(String bundleNumber) {
        this.bundleNumber = bundleNumber;
    }
    
    public String getBundleQuantity() {
        return bundleQuantity;
    }

    public void setBundleQuantity(String bundleQuantity) {
        this.bundleQuantity = bundleQuantity;
    }

    public String getNewItemExtQuoteId() {
        return newItemExtQuoteId;
    }

    public void setNewItemExtQuoteId(String newItemExtQuoteId) {
        this.newItemExtQuoteId = newItemExtQuoteId;
    }

    public String getNewItemExtQuoteItem() {
        return newItemExtQuoteItem;
    }

    public void setNewItemExtQuoteItem(String newItemExtQuoteItem) {
        this.newItemExtQuoteItem = newItemExtQuoteItem;
    }

    public String getQuoteQuantity() {
        return quoteQuantity;
    }

    public void setQuoteQuantity(String quoteQuantity) {
        this.quoteQuantity = quoteQuantity;
    }

	public String getLeadtime() {
		return leadtime;
	}

	public void setLeadtime(String leadtime) {
		this.leadtime = leadtime;
	}
  public String getItemSuppress() {
    return itemSuppress;
  }

  public void setItemSuppress(String itemSuppress) {
    this.itemSuppress = itemSuppress;
  }

  public String getItemSuppressPrice() {
    return itemSuppressPrice;
  }

  public void setItemSuppressPrice(String itemSuppressPrice) {
    this.itemSuppressPrice = itemSuppressPrice;
  }
}
