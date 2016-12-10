package com.vroozi.customerui.catalogItem.model;

import com.vroozi.customerui.util.KeyValue;

import java.util.List;

/**
 * User: SURYA MANDADAPU
 * Date: 12/17/12
 * Time: 1:47 PM
 */
public class CatalogItem {

	private String newItemMatgroup;
    private String catalogId;
    private String catalogItemId;
    private String itemType;
    private String shortDescription;
    private String currency;
    private String price;
    private String unitOfMeasure;
    private String vendorPartNumber;
    private String supplier;
    private String catagory;
    private String manufactName;
    private String manufactPart;
    private String itemUrl;
    private String contract;
    private String contractLineItem;
    private String configurable;
    private String longDescription;
    private List<KeyValue> catagories;
    private List<KeyValue> suppliers;
    private List<KeyValue> currencies;

   // private Fulfillment fulfillment;
    private String leadTime;
    private String minimumOrderQuantity;
    private String orderMultiplyQuantity;
    private boolean inStock;
    private String quantityOnHand;


//    private SapFields sapFields;
    private String newItemCustField1;
    private String newItemCustField2;
    private String newItemCustField3;
    private String newItemCustField4;
    private String newItemCustField5;
    private String newItemMatnr;
    private String newItemExtSchemaType;
    private String newItemExtCatagoryId;
    private String newItemExtCatagory;
    private String newItemSldSysName;
    private String newItemExtProductId;
    private String itemConfig;
    private boolean itemPolicy;
    private String newItemExtQuoteItem;




//    private Pricing pricing;
    public String listPrice;
    public String priceUnit;
    public boolean priceEditable;
    public List<TieredPrice> tieredPrices =null;

    public String tieredPricing;
    private String readOnly = "";
    
    private String brandName;
    private String modelno;
    private String itemOrderInterval;
    private String itemBundleNo;
    private String bundleQuantity;
    private String quoteQuantity;
    private boolean itemSuppress;
    private boolean itemSuppressPrice;

    public String getTieredPricing() {
		return tieredPricing;
	}

	public void setTieredPricing(String tieredPricing) {
		this.tieredPricing = tieredPricing;
	}

	public String getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    //    public Fulfillment getFulfillment() {
//        return fulfillment;
//    }
//
//    public void setFulfillment(Fulfillment fulfillment) {
//        this.fulfillment = fulfillment;
//    }
//
//    public SapFields getSapFields() {
//        return sapFields;
//    }
//
//    public void setSapFields(SapFields sapFields) {
//        this.sapFields = sapFields;
//    }
//
//    public Pricing getPricing() {
//        return pricing;
//    }
//
//    public void setPricing(Pricing pricing) {
//        this.pricing = pricing;
//    }

    public boolean isItemSuppressPrice() {
      return itemSuppressPrice;
    }

    public void setItemSuppressPrice(boolean itemSuppressPrice) {
      this.itemSuppressPrice = itemSuppressPrice;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogItemId() {
        return catalogItemId;
    }

    public void setCatalogItemId(String catalogItemId) {
        this.catalogItemId = catalogItemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getVendorPartNumber() {
        return vendorPartNumber;
    }

    public void setVendorPartNumber(String vendorPartNumber) {
        this.vendorPartNumber = vendorPartNumber;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getManufactName() {
        return manufactName;
    }

    public void setManufactName(String manufactName) {
        this.manufactName = manufactName;
    }

    public String getManufactPart() {
        return manufactPart;
    }

    public void setManufactPart(String manufactPart) {
        this.manufactPart = manufactPart;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getContractLineItem() {
        return contractLineItem;
    }

    public void setContractLineItem(String contractLineItem) {
        this.contractLineItem = contractLineItem;
    }

    public String getConfigurable() {
        return configurable;
    }

    public void setConfigurable(String configurable) {
        this.configurable = configurable;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public List<KeyValue> getCatagories() {
        return catagories;
    }

    public void setCatagories(List<KeyValue> catagories) {
        this.catagories = catagories;
    }

    public List<KeyValue> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<KeyValue> suppliers) {
        this.suppliers = suppliers;
    }

    public List<KeyValue> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<KeyValue> currencies) {
        this.currencies = currencies;
    }
    public String getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(String leadTime) {
        this.leadTime = leadTime;
    }

    public String getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    public void setMinimumOrderQuantity(String minimumOrderQuantity) {
        this.minimumOrderQuantity = minimumOrderQuantity;
    }

    public String getOrderMultiplyQuantity() {
        return orderMultiplyQuantity;
    }

    public void setOrderMultiplyQuantity(String orderMultiplyQuantity) {
        this.orderMultiplyQuantity = orderMultiplyQuantity;
    }

    public boolean getInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(String quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }
    public String getNewItemCustField1() {
        return newItemCustField1;
    }

    public void setNewItemCustField1(String newItemCustField1) {
        this.newItemCustField1 = newItemCustField1;
    }

    public String getNewItemCustField2() {
        return newItemCustField2;
    }

    public void setNewItemCustField2(String newItemCustField2) {
        this.newItemCustField2 = newItemCustField2;
    }

    public String getNewItemCustField3() {
        return newItemCustField3;
    }

    public void setNewItemCustField3(String newItemCustField3) {
        this.newItemCustField3 = newItemCustField3;
    }

    public String getNewItemCustField4() {
        return newItemCustField4;
    }

    public void setNewItemCustField4(String newItemCustField4) {
        this.newItemCustField4 = newItemCustField4;
    }

    public String getNewItemCustField5() {
        return newItemCustField5;
    }

    public void setNewItemCustField5(String newItemCustField5) {
        this.newItemCustField5 = newItemCustField5;
    }

    public String getNewItemMatnr() {
        return newItemMatnr;
    }

    public void setNewItemMatnr(String newItemMatnr) {
        this.newItemMatnr = newItemMatnr;
    }

    public String getNewItemExtSchemaType() {
        return newItemExtSchemaType;
    }

    public void setNewItemExtSchemaType(String newItemExtSchemaType) {
        this.newItemExtSchemaType = newItemExtSchemaType;
    }

    public String getNewItemExtCatagoryId() {
        return newItemExtCatagoryId;
    }

    public void setNewItemExtCatagoryId(String newItemExtCatagoryId) {
        this.newItemExtCatagoryId = newItemExtCatagoryId;
    }

    public String getNewItemExtCatagory() {
        return newItemExtCatagory;
    }

    public void setNewItemExtCatagory(String newItemExtCatagory) {
        this.newItemExtCatagory = newItemExtCatagory;
    }

    public String getNewItemSldSysName() {
        return newItemSldSysName;
    }

    public void setNewItemSldSysName(String newItemSldSysName) {
        this.newItemSldSysName = newItemSldSysName;
    }
    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public boolean isPriceEditable() {
        return priceEditable;
    }

    public void setPriceEditable(boolean priceEditable) {
        this.priceEditable = priceEditable;
    }

    public List<TieredPrice> getTieredPrices() {
        return tieredPrices;
    }

    public void setTieredPrices(List<TieredPrice> tieredPrices) {
        this.tieredPrices = tieredPrices;
    }

	public String getNewItemMatgroup() {
		return newItemMatgroup;
	}

	public void setNewItemMatgroup(String newItemMatgroup) {
		this.newItemMatgroup = newItemMatgroup;
	}


    public String getNewItemExtProductId() {
        return newItemExtProductId;
    }

    public void setNewItemExtProductId(String newItemExtProductId) {
        this.newItemExtProductId = newItemExtProductId;
    }

    public String getItemConfig() {
        return itemConfig;
    }

    public void setItemConfig(String itemConfig) {
        this.itemConfig = itemConfig;
    }

    public boolean isItemPolicy() {
        return itemPolicy;
    }

    public void setItemPolicy(boolean itemPolicy) {
        this.itemPolicy = itemPolicy;
    }

    public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelno() {
		return modelno;
	}

	public void setModelno(String modelno) {
		this.modelno = modelno;
	}

	public String getItemOrderInterval() {
		return itemOrderInterval;
	}

	public void setItemOrderInterval(String itemOrderInterval) {
		this.itemOrderInterval = itemOrderInterval;
	}

    public String getItemBundleNo() {
        return itemBundleNo;
    }

    public void setItemBundleNo(String itemBundleNo) {
        this.itemBundleNo = itemBundleNo;
    }

    public String getQuoteQuantity() {
        return quoteQuantity;
    }

    public void setQuoteQuantity(String quoteQuantity) {
        this.quoteQuantity = quoteQuantity;
    }

    public String getBundleQuantity() {
        return bundleQuantity;
    }

    public void setBundleQuantity(String bundleQuantity) {
        this.bundleQuantity = bundleQuantity;
    }

    public String getNewItemExtQuoteItem() {
        return newItemExtQuoteItem;
    }

    public void setNewItemExtQuoteItem(String newItemExtQuoteItem) {
        this.newItemExtQuoteItem = newItemExtQuoteItem;
    }


  public boolean isItemSuppress() {
    return itemSuppress;
  }

  public void setItemSuppress(boolean itemSuppress) {
    this.itemSuppress = itemSuppress;
  }
}
