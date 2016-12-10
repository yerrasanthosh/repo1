package com.vroozi.customerui.catalogItem.service.rest;

import java.util.*;

import com.vroozi.customerui.catalogItem.model.*;
import com.vroozi.customerui.exception.AdminUIException;

import com.vroozi.customerui.upload.model.ImageAttachmentResource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.vroozi.customerui.catalog.services.rest.CatalogRestClientImpl;
import com.vroozi.customerui.util.RestServiceUrl;

public class CatalogItemRestClientImpl implements CatalogItemRestClient{

	private final Logger logger = LoggerFactory.getLogger(CatalogRestClientImpl.class);
	
	@Autowired
    RestServiceUrl restServiceUrl;

    @Override
    public OciCatalogItemProxy getCatalogItemByUnitIdItemId(String unitId,String itemId){
        OciCatalogItemProxy catalogItemProxy = null;

        if(StringUtils.isEmpty(itemId)) throw new IllegalArgumentException("itemId can not be null or blank");

        try{
            catalogItemProxy =  new RestTemplate().getForObject(restServiceUrl.getItemURI() + "organization/"+ unitId+"/catalogitem/{itemId}", OciCatalogItemProxy.class, itemId);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return catalogItemProxy;
    }

    @Override
    public OciCatalogItemProxy getCatalogItemByCatalogIdItemId(String catalogId,String itemId){
        OciCatalogItemProxy catalogItemProxy = null;

        if(StringUtils.isEmpty(itemId)) throw new IllegalArgumentException("itemId can not be null or blank");

        try{
            catalogItemProxy =  new RestTemplate().getForObject(restServiceUrl.getItemURI() + "catalog/" + catalogId +"/catalogitem/{itemId}", OciCatalogItemProxy.class, itemId);

        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
        return  catalogItemProxy;
       // return populateCatalogItem(catalogItemProxy, item);
    }

    @Override
    public String updateCatalogItem(String catalogId,String itemId,OciCatalogItemProxy itemProxy){
        new RestTemplate().put(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId, itemProxy);
        return "OK";
    }


    @Override
    public String updateCatalogItemTieredPrice(String catalogId,String itemId,TieredPrice tieredPrice){
        new RestTemplate().put(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId+"/tiered-prices", tieredPrice);
        return "OK";
    }

    @Override
    public String associateCatalogItemAttachments(String catalogId,String itemId,ImageAttachmentResource itemProxy){
        new RestTemplate().put(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId+"/attachments", itemProxy);
        return "OK";
    }

    @Override
    public String associateCatalogItemImages(String catalogId,String itemId,ImageAttachmentResource itemProxy){
        new RestTemplate().put(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId+"/images", itemProxy);
        return "OK";
    }

    public String updateCatalogItemImages(String catalogId, String itemId,ImageAttachmentResource fileProxy){
        new RestTemplate().put(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId+"/images/"+fileProxy.fileResourceId, fileProxy);
        return "OK";
    }

    public String removeCatalogItemImages(String catalogId, String itemId,String imageIds){
        new RestTemplate().delete(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId+"/images/"+imageIds);
        return "OK";
    }

    public void activateCatalogItemCustomFields(String catalogId,String itemId,String customFieldIds){
        new RestTemplate().put(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId+"/customfields/"+customFieldIds+"/activate",new OciCatalogItemProxy() );
    }
    public void deActivateCatalogItemCustomFields(String catalogId,String itemId,String customFieldIds)    {
        new RestTemplate().put(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId+"/customfields/"+customFieldIds+"/deactivate",new OciCatalogItemProxy());
    }
    public void deleteCatalogItemCustomFields(String catalogId,String itemId,String customFieldIds){
        new RestTemplate().delete(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId+"/customfields/"+customFieldIds);
    }
    public void addCatalogItemCustomFields(String catalogId,String itemId,String customFieldIds){
        new RestTemplate().put(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId+"/customfields/"+customFieldIds, null);
    }


    @Override
    public String removeCatalogItemAttachments(String catalogId,String itemId,String attachmentids){
        new RestTemplate().delete(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId+"/attachments/"+attachmentids);
        return "OK";
    }
//    @Override
//    public List<ImageAttachmentResource> getAttachmentsByUnitId(String unitId){
//        List<ImageAttachmentResource> imageAttachmentResources = new ArrayList<ImageAttachmentResource>();
//
//        if(StringUtils.isEmpty(unitId)) throw new IllegalArgumentException("catalog 'unitId' can not be null or blank");
//
//        try {
//            ImageAttachmentResource[] catalogSummaries =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "organization/{unitId}/attachments", ImageAttachmentResource[].class, unitId);
//            imageAttachmentResources = new ArrayList<ImageAttachmentResource>(Arrays.asList(catalogSummaries));
//            return imageAttachmentResources;
//        } catch(RestClientException rse) {
//            logger.error("Error retrieving catalogs!", rse);
//            throw rse;
//        } catch(Exception exp) {
//            logger.error("Error retrieving catalogs!", exp);
//            throw new AdminUIException(exp);
//        }
//    }
    @Override
    public List<ImageAttachmentResource> getCatalogItemAttachments(String catalogId, String catalogItemId){
        List<ImageAttachmentResource> imageAttachmentResources = new ArrayList<ImageAttachmentResource>();
        try {
            ///catalogs/{catalogid}/catalogitems/{catalogitemid}/images
            ImageAttachmentResource[] catalogAttachments =  new RestTemplate().getForObject(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + catalogItemId+"/attachments", ImageAttachmentResource[].class);
            if(catalogAttachments!=null){
                imageAttachmentResources = new ArrayList<ImageAttachmentResource>(Arrays.asList(catalogAttachments));
            }
            return imageAttachmentResources;
        } catch(RestClientException rse) {
            logger.error("Error retrieving catalogs!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving catalogs!", exp);
            throw new AdminUIException(exp);
        }
    }
    @Override
    public String updateCatalogItemAttachments(String catalogId,String itemId,ImageAttachmentResource itemProxy){
        new RestTemplate().put(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + itemId+"/attachments/"+itemProxy.fileResourceId, itemProxy);
        return "OK";
    }
    @Override
    public List<ImageAttachmentResource> getCatalogItemImages(String catalogId, String catalogItemId){
        List<ImageAttachmentResource> imageResources = new ArrayList<ImageAttachmentResource>();
        try {
            ///catalogs/{catalogid}/catalogitems/{catalogitemid}/images
            ImageAttachmentResource[] catalogImages =  new RestTemplate().getForObject(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + catalogItemId+"/images", ImageAttachmentResource[].class);
            if(catalogImages!=null){
                imageResources = new ArrayList<ImageAttachmentResource>(Arrays.asList(catalogImages));
            }
            return imageResources;
        } catch(RestClientException rse) {
            logger.error("Error retrieving catalogs!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving catalogs!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public List<TieredPrice> getCatalogItemTieredPrices(String catalogId, String catalogItemId){
        List<TieredPrice> tieredResources = new ArrayList<TieredPrice>();
        try {
            ///catalogs/{catalogid}/catalogitems/{catalogitemid}/images
            TieredPrice[] tieredPrices =  new RestTemplate().getForObject(restServiceUrl.getItemURI() + catalogId+ "/catalogitems/" + catalogItemId+"/tiered-prices", TieredPrice[].class);
            if(tieredPrices!=null){
                tieredResources = new ArrayList<TieredPrice>(Arrays.asList(tieredPrices));
            }
            return tieredResources;
        } catch(RestClientException rse) {
            logger.error("Error retrieving catalogs!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving catalogs!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public String createCatalogItem(String catalogId,OciCatalogItemProxy itemProxy){
        RestTemplate template =new RestTemplate();
        String resmessage = (String)template.postForObject(restServiceUrl.getItemURI() + catalogId+"/catalogitems", itemProxy, String.class);
        return resmessage;
    }

    @Override
    public long findCatalogByBundleNameAndUnitId(String catalogId, String itemBundleNo, String unitId) {
        RestTemplate template =new RestTemplate();
        Long resmessage = (Long)template.getForObject(restServiceUrl.getItemURI() + catalogId+"/catalogitems/"+itemBundleNo+"/"+unitId, Long.class);
        return resmessage;

    }

//    private CatalogItem populateCatalogItem(OciCatalogItemProxy catalogItemProxy,CatalogItem catalogItem){
//        if(catalogItemProxy != null){
//            catalogItem.setCatalogId(catalogItemProxy.getCatalogId());
//            catalogItem.setCatalogItemId(catalogItemProxy.getCatalogItemId());
//            catalogItem.setItemType(catalogItemProxy.getNewItemService()!=null?catalogItemProxy.getNewItemService():"P");
//            catalogItem.setShortDescription(catalogItemProxy.getNewItemDescription()!=null?catalogItemProxy.getNewItemDescription():"");
//            catalogItem.setCurrency(catalogItemProxy.getNewItemCurrency()!=null?catalogItemProxy.getNewItemCurrency():"");
//            catalogItem.setPrice(catalogItemProxy.getPrice()!=null?catalogItemProxy.getPrice():"");
//            catalogItem.setUnitOfMeasure(catalogItemProxy.getNewItemUnit()!=null?catalogItemProxy.getNewItemUnit():"");
//            catalogItem.setVendorPartNumber(catalogItemProxy.getNewItemVendormat()!=null?catalogItemProxy.getNewItemVendormat():"");
//            catalogItem.setSupplier(catalogItemProxy.getNewItemVendor()!=null?catalogItemProxy.getNewItemVendor():"");
//            catalogItem.setManufactName(catalogItemProxy.getItemManufacturerName()!=null?catalogItemProxy.getItemManufacturerName():"");
//            catalogItem.setManufactPart(catalogItemProxy.getNewItemManufactmat()!=null?catalogItemProxy.getNewItemManufactmat():"");
//            catalogItem.setItemUrl(catalogItemProxy.getItemUrl()!=null?catalogItemProxy.getItemUrl():"");
//            catalogItem.setContract(catalogItemProxy.getNewItemContractItem()!=null?catalogItemProxy.getNewItemContractItem():"");
//            catalogItem.setContractLineItem(catalogItemProxy.getNewItemContractItem()!=null?catalogItemProxy.getNewItemContractItem():"");
//            catalogItem.setConfigurable((catalogItemProxy.getConfigurable()!=null&&catalogItemProxy.getConfigurable()==1)?"true":"false");
//            catalogItem.setLongDescription(catalogItemProxy.getNewItemLongtext1()!=null?catalogItemProxy.getNewItemLongtext1():"");
//
//            //Fulfillment fulfillment = new Fulfillment();
//            catalogItem.setInStock(catalogItemProxy.getItemInstock()!=null?true:false);
//            catalogItem.setLeadTime(catalogItemProxy.getNewItemLeadtime() != null ? catalogItemProxy.getNewItemLeadtime() : "");
//            catalogItem.setMinimumOrderQuantity(catalogItemProxy.getItemMinOrderQty()!=null?catalogItemProxy.getItemMinOrderQty():"");
//            catalogItem.setOrderMultiplyQuantity("");
//            catalogItem.setQuantityOnHand(catalogItemProxy.getItemQtyOnhand()!=null?catalogItemProxy.getItemQtyOnhand():"");
//           // catalogItem.setFulfillment(fulfillment);
//
//            //SapFields sapFields = new SapFields();
//            catalogItem.setNewItemCustField1(catalogItemProxy.getNewItemCustField1() != null ? catalogItemProxy.getNewItemCustField1() : "");
//            catalogItem.setNewItemCustField2(catalogItemProxy.getNewItemCustField2() != null ? catalogItemProxy.getNewItemCustField2() : "");
//            catalogItem.setNewItemCustField3(catalogItemProxy.getNewItemCustField3() != null ? catalogItemProxy.getNewItemCustField3() : "");
//            catalogItem.setNewItemCustField4(catalogItemProxy.getNewItemCustField4() != null ? catalogItemProxy.getNewItemCustField4() : "");
//            catalogItem.setNewItemCustField5(catalogItemProxy.getNewItemCustField5() != null ? catalogItemProxy.getNewItemCustField5() : "");
//            catalogItem.setNewItemMatnr(catalogItemProxy.getNewItemMatnr() != null ? catalogItemProxy.getNewItemMatnr() : "");
//            catalogItem.setNewItemExtSchemaType(catalogItemProxy.getNewItemExtSchemaType() != null ? catalogItemProxy.getNewItemExtSchemaType() : "");
//            catalogItem.setNewItemExtCatagoryId(catalogItemProxy.getNewItemExtCatagoryId() != null ? catalogItemProxy.getNewItemExtCatagoryId() : "");
//            catalogItem.setNewItemExtCatagory(catalogItemProxy.getNewItemExtCatagory() != null ? catalogItemProxy.getNewItemExtCatagory() : "");
//            catalogItem.setNewItemSldSysName(catalogItemProxy.getNewItemSldSysName() != null ? catalogItemProxy.getNewItemSldSysName() : "");
//            //catalogItem.setSapFields(sapFields);
//
//            //Pricing pricing = new Pricing();
//            catalogItem.setListPrice(catalogItemProxy.getItemListPrice() != null ? catalogItemProxy.getItemListPrice() : "");
//            catalogItem.setPriceUnit(catalogItemProxy.getNewItemPriceunit() != null ? catalogItemProxy.getNewItemPriceunit() : "");
//            catalogItem.setPriceEditable(true);
//
//            // To DO for alfie test only.
//            List<TieredPrice> testTieredPrices  = new ArrayList<TieredPrice>();
//            TieredPrice tieredPrice = new TieredPrice();
//            tieredPrice.setFrom(1);
//            tieredPrice.setTo(5);
//            tieredPrice.setPrice("$50.00");
//            testTieredPrices.add(tieredPrice);
//
//
//
//            catalogItem.setTieredPrices(catalogItemProxy.getTieredPrices() != null ? catalogItemProxy.getTieredPrices() : testTieredPrices);
//            ///catalogItem.setPricing(pricing);
//                                                    }
//        return catalogItem;
//    }

}
