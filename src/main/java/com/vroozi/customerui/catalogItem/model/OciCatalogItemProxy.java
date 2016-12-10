package com.vroozi.customerui.catalogItem.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.vroozi.customerui.upload.model.ImageAttachmentResource;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OciCatalogItemProxy extends CatalogItemProxy implements Serializable {
    private String itemId;
    private Long itemSerialNumber;
    private Integer ociItemStatusId;
    private String newItemDescription;
    private String newItemMatnr;
    private Long newItemQuantity;
    private String newItemUnit;
    private String newItemPriceunit;
    private String newItemCurrency;
    private String newItemLeadtime;
    private String newItemVendor;
    private String newItemVendormat;
    private String newItemManufactcode;
    private String newItemManufactmat;
    private String newItemContract;
    private String newItemContractItem;
    private String newItemExtQuoteId;
    private String newItemExtQuoteItem;
    private String newItemExtProductId;
    private String newItemMatgroup;
    private String newItemLongtext1;
    private String newItemCustField1;
    private String newItemCustField2;
    private String newItemCustField3;
    private String newItemCustField4;
    private String newItemCustField5;
    private String itemImage;
    private String itemUrl;
    private String newItemParentId;
    private String newItemItemType;
    private String newItemAttachment;
    private String newItemAttachmentTitle;
    private String newItemAttachmentPurpose;
    private String newItemExtSchemaType;
    private String newItemExtCatagoryId;
    private String newItemExtCatagory;
    private String newItemSldSysName;
    private String newItemService;
    private String quoteQuantity;
    private String price;
    private String tieredPricing;
    private String itemBundleNo;
    private Integer greenItem;
    private String itemBundleName;
    private Integer configurable;
    private Integer published;
    private String itemInstock;
    private String itemQtyOnhand;

    private String itemManufacturerName;
    private String itemBrandName;
    private String itemMinOrderQty;
    private String itemModelno;
    private String itemUpc;
    private String itemCaseUpc;
    private String itemListPrice;
    private String itemColor;
    private String itemSize;
    private String itemGender;
    private String itemRam;
    private String itemCpu;
    private String itemHdd;
    private String itemScreenSz;
    private String itemScreenRez;
    private String itemComments;
    private String itemConfig;
    private String itemPolicy;
//    private Integer itemEnergyStar;

    private Integer deleted;
    private Character revisionChange;
    private Integer revisionTag;

    private String searchable;
    public boolean priceEditable;

    private String orderMultiplyQuantity;
    private Double rating;
    private Integer ratingCount;
    
    public String updateStatus;
    private List<OciItemCustomFieldValue> customFieldItems = null;
    public List<ImageAttachmentResource> attachments =null;
    public List<ImageAttachmentResource> images =null;
    public List<TieredPrice> tieredPrices =null;

    public Boolean itemEnergyStar;
    private Boolean itemFreeShip;
    private Boolean itemClearance;
    private Boolean itemOnSale;
    private Boolean itemPreferred;
    private Boolean itemRefurb;
    private Boolean itemContract;
    private Boolean itemRohs;
    private String itemOrderInterval;
    private String parentItemId;
    private String bundleQuantity;
    private String itemSuppress;
    private String itemSuppressPrice;
    public OciCatalogItemProxy() {
    }

    
    
    
    
    public String getParentItemId() {
		return parentItemId;
	}





	public void setParentItemId(String parentItemId) {
		this.parentItemId = parentItemId;
	}





	public String getUpdateStatus() {
		return updateStatus;
	}





	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}





	public String getOrderMultiplyQuantity() {
		return orderMultiplyQuantity;
	}





	public void setOrderMultiplyQuantity(String orderMultiplyQuantity) {
		this.orderMultiplyQuantity = orderMultiplyQuantity;
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

    public List<ImageAttachmentResource> getImages() {
        return images;
    }

    public void setImages(List<ImageAttachmentResource> images) {
        this.images = images;
    }

    public List<ImageAttachmentResource> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ImageAttachmentResource> attachments) {
        this.attachments = attachments;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

  public Long getItemSerialNumber() {
    return itemSerialNumber;
  }

  public void setItemSerialNumber(Long itemSerialNumber) {
    this.itemSerialNumber = itemSerialNumber;
  }

  public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getOciItemStatusId() {
        return ociItemStatusId;
    }

    public void setOciItemStatusId(Integer ociItemStatusId) {
        this.ociItemStatusId = ociItemStatusId;
    }

    public String getNewItemDescription() {
        return newItemDescription;
    }

    public void setNewItemDescription(String newItemDescription) {
        this.newItemDescription = newItemDescription;
    }

    public String getNewItemMatnr() {
        return newItemMatnr;
    }

    public void setNewItemMatnr(String newItemMatnr) {
        this.newItemMatnr = newItemMatnr;
    }

    public Long getNewItemQuantity() {
        return newItemQuantity;
    }

    public void setNewItemQuantity(Long newItemQuantity) {
        this.newItemQuantity = newItemQuantity;
    }

    public String getNewItemUnit() {
        return newItemUnit;
    }

    public void setNewItemUnit(String newItemUnit) {
        this.newItemUnit = newItemUnit;
    }

    public String getNewItemPriceunit() {
       return newItemPriceunit;

    }

    public void setNewItemPriceunit(String newItemPriceunit) {
        this.newItemPriceunit = newItemPriceunit;
    }

    public String getNewItemCurrency() {
        return newItemCurrency;
    }

    public void setNewItemCurrency(String newItemCurrency) {
        this.newItemCurrency = newItemCurrency;
    }

    public String getNewItemLeadtime() {
        return newItemLeadtime;
    }

    public void setNewItemLeadtime(String newItemLeadtime) {
        this.newItemLeadtime = newItemLeadtime;
    }

    public String getNewItemVendor() {
        return newItemVendor;
    }

    public void setNewItemVendor(String newItemVendor) {
        this.newItemVendor = newItemVendor;
    }

    public String getNewItemVendormat() {
        return newItemVendormat;
    }

    public void setNewItemVendormat(String newItemVendormat) {
        this.newItemVendormat = newItemVendormat;
    }

    public String getNewItemManufactcode() {
        return newItemManufactcode;
    }

    public void setNewItemManufactcode(String newItemManufactcode) {
        this.newItemManufactcode = newItemManufactcode;
    }

    public String getNewItemManufactmat() {
        return newItemManufactmat;
    }

    public void setNewItemManufactmat(String newItemManufactmat) {
        this.newItemManufactmat = newItemManufactmat;
    }

    public String getNewItemContract() {
        return newItemContract;
    }

    public void setNewItemContract(String newItemContract) {
        this.newItemContract = newItemContract;
    }

    public String getNewItemContractItem() {
        return newItemContractItem;
    }

    public void setNewItemContractItem(String newItemContractItem) {
        this.newItemContractItem = newItemContractItem;
    }

    public String getNewItemService() {
        return newItemService;
    }

    public void setNewItemService(String newItemService) {
        this.newItemService = newItemService;
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

    public String getNewItemExtProductId() {
        return newItemExtProductId;
    }

    public void setNewItemExtProductId(String newItemExtProductId) {
        this.newItemExtProductId = newItemExtProductId;
    }

    public String getNewItemMatgroup() {
        return newItemMatgroup;
    }

    public void setNewItemMatgroup(String newItemMatgroup) {
        this.newItemMatgroup = newItemMatgroup;
    }

    public String getNewItemLongtext1() {
        return newItemLongtext1;
    }

    public void setNewItemLongtext1(String newItemLongtext1) {
        this.newItemLongtext1 = newItemLongtext1;
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

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getNewItemParentId() {
        return newItemParentId;
    }

    public void setNewItemParentId(String newItemParentId) {
        this.newItemParentId = newItemParentId;
    }

    public String getNewItemItemType() {
        return newItemItemType;
    }

    public void setNewItemItemType(String newItemItemType) {
        this.newItemItemType = newItemItemType;
    }

    public String getNewItemAttachment() {
        return newItemAttachment;
    }

    public void setNewItemAttachment(String newItemAttachment) {
        this.newItemAttachment = newItemAttachment;
    }

    public String getNewItemAttachmentTitle() {
        return newItemAttachmentTitle;
    }

    public void setNewItemAttachmentTitle(String newItemAttachmentTitle) {
        this.newItemAttachmentTitle = newItemAttachmentTitle;
    }

    public String getNewItemAttachmentPurpose() {
        return newItemAttachmentPurpose;
    }

    public void setNewItemAttachmentPurpose(String newItemAttachmentPurpose) {
        this.newItemAttachmentPurpose = newItemAttachmentPurpose;
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

    public String getQuoteQuantity() {
        return quoteQuantity;
    }

    public void setQuoteQuantity(String quoteQuantity) {
        this.quoteQuantity = quoteQuantity;
    }

    public String getPrice() {
       // return price;
    	try{
	        if(price!=null && !"".equalsIgnoreCase(price.trim())){
	        	if(Double.parseDouble(price) % 1 !=0) {
	        		BigDecimal d = new BigDecimal(price);
	        		return d.setScale(4, BigDecimal.ROUND_UP).stripTrailingZeros().toString();
	        	}
	        	return price;
	        }else{
	            return price;
	        }
    	}catch (Exception e) {
    		return price;
		}
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTieredPricing() {
        return tieredPricing;
    }

    public void setTieredPricing(String tieredPricing) {
        this.tieredPricing = tieredPricing;
    }

    public String getItemBundleNo() {
        return itemBundleNo;
    }

    public void setItemBundleNo(String itemBundleNo) {
        this.itemBundleNo = itemBundleNo;
    }

    public Integer getGreenItem() {
        return greenItem;
    }

    public void setGreenItem(Integer greenItem) {
        this.greenItem = greenItem;
    }

    public String getItemBundleName() {
        return itemBundleName;
    }

    public void setItemBundleName(String itemBundleName) {
        this.itemBundleName = itemBundleName;
    }

    public Integer getConfigurable() {
        return configurable;
    }

    public void setConfigurable(Integer configurable) {
        this.configurable = configurable;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public String getItemInstock() {
        return itemInstock;
    }

    public void setItemInstock(String itemInstock) {
        this.itemInstock = itemInstock;
    }

    public String getItemQtyOnhand() {
        return itemQtyOnhand;
    }

    public void setItemQtyOnhand(String itemQtyOnhand) {
        this.itemQtyOnhand = itemQtyOnhand;
    }

    public String getItemManufacturerName() {
        return itemManufacturerName;
    }

    public void setItemManufacturerName(String itemManufacturerName) {
        this.itemManufacturerName = itemManufacturerName;
    }

    public String getItemBrandName() {
        return itemBrandName;
    }

    public void setItemBrandName(String itemBrandName) {
        this.itemBrandName = itemBrandName;
    }

    public String getItemMinOrderQty() {
        return itemMinOrderQty;
    }

    public void setItemMinOrderQty(String itemMinOrderQty) {
        this.itemMinOrderQty = itemMinOrderQty;
    }

    public String getItemModelno() {
        return itemModelno;
    }

    public void setItemModelno(String itemModelno) {
        this.itemModelno = itemModelno;
    }

    public String getItemUpc() {
        return itemUpc;
    }

    public void setItemUpc(String itemUpc) {
        this.itemUpc = itemUpc;
    }

    public String getItemCaseUpc() {
        return itemCaseUpc;
    }

    public void setItemCaseUpc(String itemCaseUpc) {
        this.itemCaseUpc = itemCaseUpc;
    }

    public String getItemListPrice() {
        return itemListPrice;
    }

    public void setItemListPrice(String itemListPrice) {
        this.itemListPrice = itemListPrice;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemGender() {
        return itemGender;
    }

    public void setItemGender(String itemGender) {
        this.itemGender = itemGender;
    }

    public String getItemRam() {
        return itemRam;
    }

    public void setItemRam(String itemRam) {
        this.itemRam = itemRam;
    }

    public String getItemCpu() {
        return itemCpu;
    }

    public void setItemCpu(String itemCpu) {
        this.itemCpu = itemCpu;
    }

    public String getItemHdd() {
        return itemHdd;
    }

    public void setItemHdd(String itemHdd) {
        this.itemHdd = itemHdd;
    }

    public String getItemScreenSz() {
        return itemScreenSz;
    }

    public void setItemScreenSz(String itemScreenSz) {
        this.itemScreenSz = itemScreenSz;
    }

    public String getItemScreenRez() {
        return itemScreenRez;
    }

    public void setItemScreenRez(String itemScreenRez) {
        this.itemScreenRez = itemScreenRez;
    }

    public String getItemComments() {
        return itemComments;
    }

    public void setItemComments(String itemComments) {
        this.itemComments = itemComments;
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

    public Boolean getItemEnergyStar() {
        return itemEnergyStar;
    }

    public void setItemEnergyStar(Boolean itemEnergyStar) {
        this.itemEnergyStar = itemEnergyStar;
    }


    public Character getRevisionChange() {
        return revisionChange;
    }

    public void setRevisionChange(Character revisionChange) {
        this.revisionChange = revisionChange;
    }

    public Integer getRevisionTag() {
        return revisionTag;
    }

    public void setRevisionTag(Integer revisionTag) {
        this.revisionTag = revisionTag;
    }

    public String getSearchable() {
        return searchable;
    }

    public void setSearchable(String searchable) {
        this.searchable = searchable;
    }

    public List<OciItemCustomFieldValue> getCustomFieldItems() {
        return customFieldItems;
    }

    public void setCustomFieldItems(List<OciItemCustomFieldValue> customFieldItems) {
        this.customFieldItems = customFieldItems;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OciCatalogItemProxy that = (OciCatalogItemProxy) o;

        if (configurable != that.configurable) return false;
        if (greenItem != that.greenItem) return false;
        if (itemEnergyStar != that.itemEnergyStar) return false;
        if (newItemQuantity != that.newItemQuantity) return false;
        if (published != that.published) return false;
        if (itemBrandName != null ? !itemBrandName.equals(that.itemBrandName) : that.itemBrandName != null)
            return false;
        if (itemBundleName != null ? !itemBundleName.equals(that.itemBundleName) : that.itemBundleName != null)
            return false;
        if (itemBundleNo != null ? !itemBundleNo.equals(that.itemBundleNo) : that.itemBundleNo != null) return false;
        if (itemCaseUpc != null ? !itemCaseUpc.equals(that.itemCaseUpc) : that.itemCaseUpc != null) return false;
        if (itemColor != null ? !itemColor.equals(that.itemColor) : that.itemColor != null) return false;
        if (itemComments != null ? !itemComments.equals(that.itemComments) : that.itemComments != null) return false;
        if (itemConfig != null ? !itemConfig.equals(that.itemConfig) : that.itemConfig != null) return false;
        if (itemPolicy != null ? !itemPolicy.equals(that.itemPolicy) : that.itemPolicy != null) return false;
        if (itemCpu != null ? !itemCpu.equals(that.itemCpu) : that.itemCpu != null) return false;
        if (itemGender != null ? !itemGender.equals(that.itemGender) : that.itemGender != null) return false;
        if (itemHdd != null ? !itemHdd.equals(that.itemHdd) : that.itemHdd != null) return false;
        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (itemImage != null ? !itemImage.equals(that.itemImage) : that.itemImage != null) return false;
        if (itemInstock != null ? !itemInstock.equals(that.itemInstock) : that.itemInstock != null) return false;
        if (itemListPrice != null ? !itemListPrice.equals(that.itemListPrice) : that.itemListPrice != null)
            return false;
        if (itemManufacturerName != null ? !itemManufacturerName.equals(that.itemManufacturerName) : that.itemManufacturerName != null)
            return false;
        if (itemMinOrderQty != null ? !itemMinOrderQty.equals(that.itemMinOrderQty) : that.itemMinOrderQty != null)
            return false;
        if (itemModelno != null ? !itemModelno.equals(that.itemModelno) : that.itemModelno != null) return false;
        if (itemQtyOnhand != null ? !itemQtyOnhand.equals(that.itemQtyOnhand) : that.itemQtyOnhand != null)
            return false;
        if (itemRam != null ? !itemRam.equals(that.itemRam) : that.itemRam != null) return false;
        if (itemScreenRez != null ? !itemScreenRez.equals(that.itemScreenRez) : that.itemScreenRez != null)
            return false;
        if (itemScreenSz != null ? !itemScreenSz.equals(that.itemScreenSz) : that.itemScreenSz != null) return false;
        if (itemSize != null ? !itemSize.equals(that.itemSize) : that.itemSize != null) return false;
        if (itemUpc != null ? !itemUpc.equals(that.itemUpc) : that.itemUpc != null) return false;
        if (itemUrl != null ? !itemUrl.equals(that.itemUrl) : that.itemUrl != null) return false;
        if (newItemContract != null ? !newItemContract.equals(that.newItemContract) : that.newItemContract != null)
            return false;
        if (newItemContractItem != null ? !newItemContractItem.equals(that.newItemContractItem) : that.newItemContractItem != null)
            return false;
        if (newItemCurrency != null ? !newItemCurrency.equals(that.newItemCurrency) : that.newItemCurrency != null)
            return false;
        if (newItemCustField1 != null ? !newItemCustField1.equals(that.newItemCustField1) : that.newItemCustField1 != null)
            return false;
        if (newItemCustField2 != null ? !newItemCustField2.equals(that.newItemCustField2) : that.newItemCustField2 != null)
            return false;
        if (newItemCustField3 != null ? !newItemCustField3.equals(that.newItemCustField3) : that.newItemCustField3 != null)
            return false;
        if (newItemCustField4 != null ? !newItemCustField4.equals(that.newItemCustField4) : that.newItemCustField4 != null)
            return false;
        if (newItemCustField5 != null ? !newItemCustField5.equals(that.newItemCustField5) : that.newItemCustField5 != null)
            return false;
        if (newItemDescription != null ? !newItemDescription.equals(that.newItemDescription) : that.newItemDescription != null)
            return false;
        if (newItemExtCatagory != null ? !newItemExtCatagory.equals(that.newItemExtCatagory) : that.newItemExtCatagory != null)
            return false;
        if (newItemExtCatagoryId != null ? !newItemExtCatagoryId.equals(that.newItemExtCatagoryId) : that.newItemExtCatagoryId != null)
            return false;
        if (newItemExtProductId != null ? !newItemExtProductId.equals(that.newItemExtProductId) : that.newItemExtProductId != null)
            return false;
        if (newItemExtQuoteId != null ? !newItemExtQuoteId.equals(that.newItemExtQuoteId) : that.newItemExtQuoteId != null)
            return false;
        if (newItemExtQuoteItem != null ? !newItemExtQuoteItem.equals(that.newItemExtQuoteItem) : that.newItemExtQuoteItem != null)
            return false;
        if (newItemExtSchemaType != null ? !newItemExtSchemaType.equals(that.newItemExtSchemaType) : that.newItemExtSchemaType != null)
            return false;
        if (newItemItemType != null ? !newItemItemType.equals(that.newItemItemType) : that.newItemItemType != null)
            return false;
        if (newItemLeadtime != null ? !newItemLeadtime.equals(that.newItemLeadtime) : that.newItemLeadtime != null)
            return false;
        if (newItemLongtext1 != null ? !newItemLongtext1.equals(that.newItemLongtext1) : that.newItemLongtext1 != null)
            return false;
        if (newItemManufactcode != null ? !newItemManufactcode.equals(that.newItemManufactcode) : that.newItemManufactcode != null)
            return false;
        if (newItemManufactmat != null ? !newItemManufactmat.equals(that.newItemManufactmat) : that.newItemManufactmat != null)
            return false;
        if (newItemMatgroup != null ? !newItemMatgroup.equals(that.newItemMatgroup) : that.newItemMatgroup != null)
            return false;
        if (newItemMatnr != null ? !newItemMatnr.equals(that.newItemMatnr) : that.newItemMatnr != null) return false;
        if (newItemParentId != null ? !newItemParentId.equals(that.newItemParentId) : that.newItemParentId != null)
            return false;
        if (newItemPriceunit != null ? !newItemPriceunit.equals(that.newItemPriceunit) : that.newItemPriceunit != null)
            return false;
        if (newItemService != null ? !newItemService.equals(that.newItemService) : that.newItemService != null)
            return false;
        if (newItemSldSysName != null ? !newItemSldSysName.equals(that.newItemSldSysName) : that.newItemSldSysName != null)
            return false;
        if (newItemUnit != null ? !newItemUnit.equals(that.newItemUnit) : that.newItemUnit != null) return false;
        if (newItemVendor != null ? !newItemVendor.equals(that.newItemVendor) : that.newItemVendor != null)
            return false;
        if (newItemVendormat != null ? !newItemVendormat.equals(that.newItemVendormat) : that.newItemVendormat != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (quoteQuantity != null ? !quoteQuantity.equals(that.quoteQuantity) : that.quoteQuantity != null)
            return false;
        if (tieredPricing != null ? !tieredPricing.equals(that.tieredPricing) : that.tieredPricing != null)
            return false;
        if(bundleQuantity != null ? !bundleQuantity.equals(that.bundleQuantity) : that.bundleQuantity != null)
            return false;
        return true;
    }


    public String getItemSuppressPrice() {
      return itemSuppressPrice;
    }

    public void setItemSuppressPrice(String itemSuppressPrice) {
      this.itemSuppressPrice = itemSuppressPrice;
    }

    public Boolean getItemFreeShip() {
        return itemFreeShip;
    }

    public void setItemFreeShip(Boolean itemFreeShip) {
        this.itemFreeShip = itemFreeShip;
    }

    public Boolean getItemClearance() {
        return itemClearance;
    }

    public void setItemClearance(Boolean itemClearance) {
        this.itemClearance = itemClearance;
    }

    public Boolean getItemOnSale() {
        return itemOnSale;
    }

    public void setItemOnSale(Boolean itemOnSale) {
        this.itemOnSale = itemOnSale;
    }

    public Boolean getItemPreferred() {
        return itemPreferred;
    }

    public void setItemPreferred(Boolean itemPreferred) {
        this.itemPreferred = itemPreferred;
    }

    public Boolean getItemRefurb() {
        return itemRefurb;
    }

    public void setItemRefurb(Boolean itemRefurb) {
        this.itemRefurb = itemRefurb;
    }

    public Boolean getItemContract() {
        return itemContract;
    }

    public void setItemContract(Boolean itemContract) {
        this.itemContract = itemContract;
    }

    public Boolean getItemRohs() {
        return itemRohs;
    }

    public void setItemRohs(Boolean itemRohs) {
        this.itemRohs = itemRohs;
    }

    public String getItemOrderInterval() {
        return itemOrderInterval;
    }

    public void setItemOrderInterval(String itemOrderInterval) {
        this.itemOrderInterval = itemOrderInterval;
    }

    public String getBundleQuantity() {
        return bundleQuantity;
    }

    public void setBundleQuantity(String bundleQuantity) {
        this.bundleQuantity = bundleQuantity;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

  public String getItemSuppress() {
    return itemSuppress;
  }

  public void setItemSuppress(String itemSuppress) {
    this.itemSuppress = itemSuppress;
  }
}
