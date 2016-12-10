package com.vroozi.customerui.catalogItem.service.rest;

import com.vroozi.customerui.catalogItem.model.CatalogItem;
import com.vroozi.customerui.catalogItem.model.OciCatalogItemProxy;
import com.vroozi.customerui.catalogItem.model.TieredPrice;
import com.vroozi.customerui.upload.model.ImageAttachmentResource;

import java.util.HashMap;
import java.util.List;


public interface CatalogItemRestClient {

	
//	public int updateItemStatus(HashMap<String, Object> params)throws Exception;

    public OciCatalogItemProxy getCatalogItemByUnitIdItemId(String unitId,String itemId);
    public OciCatalogItemProxy getCatalogItemByCatalogIdItemId(String catalogId,String itemId);
    public String updateCatalogItem(String catalogId,String itemId,OciCatalogItemProxy proxy);
    public String updateCatalogItemAttachments(String catalogId,String itemId,ImageAttachmentResource itemProxy);
    public List<ImageAttachmentResource> getCatalogItemAttachments(String catalogId, String catalogItemId);
   public String associateCatalogItemAttachments(String catalogId,String itemId,ImageAttachmentResource itemProxy);

    public List<ImageAttachmentResource> getCatalogItemImages(String catalogId, String catalogItemId);
    public String removeCatalogItemAttachments(String catalogId,String itemId,String attachmentIds);


    public List<TieredPrice> getCatalogItemTieredPrices(String catalogId, String catalogItemId);
    public String updateCatalogItemTieredPrice(String catalogId,String itemId,TieredPrice tieredPrice);
    public String associateCatalogItemImages(String catalogId,String itemId,ImageAttachmentResource itemProxy);
    public String updateCatalogItemImages(String catalogId, String itemId,ImageAttachmentResource fileProxy);
    public String removeCatalogItemImages(String catalogId, String itemId,String imageIds);

    public void activateCatalogItemCustomFields(String catalogId,String itemId,String customFieldIds);
    public void deActivateCatalogItemCustomFields(String catalogId,String itemId,String customFieldIds);
    public void deleteCatalogItemCustomFields(String catalogId,String itemId,String customFieldIds);
    public void addCatalogItemCustomFields(String catalogId,String itemId,String customFieldIds);


        // public List<ImageAttachmentResource> getAttachmentsByUnitId(String unitId);
    public String createCatalogItem(String catalogId,OciCatalogItemProxy proxy);

    long findCatalogByBundleNameAndUnitId(String catalogId, String itemBundleNo, String unitId);
}
