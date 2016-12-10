package com.vroozi.customerui.catalogItem.controller;

import com.vroozi.customerui.catalog.common.CatalogDataDisplayPopulator;
import com.vroozi.customerui.catalog.model.CatalogCustomField;
import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.catalog.services.CatalogService;
import com.vroozi.customerui.catalogItem.model.*;
import com.vroozi.customerui.catalogItem.service.rest.CatalogItemRestClient;
import com.vroozi.customerui.common.BaseController;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.matgroup.services.rest.MaterialGroupRestClient;
import com.vroozi.customerui.supplier.model.CurrencyCode;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.services.rest.SupplierRestClient;
import com.vroozi.customerui.upload.model.FileResourceForm;
import com.vroozi.customerui.upload.model.ImageAttachmentResource;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.BinaryInsertSortCustomFieldsOnDisplayOrder;
import com.vroozi.customerui.util.DecimalNotation;
import com.vroozi.customerui.util.KeyValue;
import com.vroozi.customerui.util.Page;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.vroozi.customerui.util.Consts.ASS_ITEM_CUSTOM_FIELD_SECTION;
import static com.vroozi.customerui.util.Consts.FAILURE;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * User: SURYA MANDADAPU
 * Date: 8/7/12
 * Time: 1:39 PM
 */
@Controller
public class CatalogItemController extends BaseController {
    private static final Logger LOGGER = getLogger(CatalogItemController.class.getName());

    private static final String STATUS_ACTIVATE = "ACTIVATE";
    private static final String STATUS_DEACTIVATE = "DEACTIVATE";

    @Autowired
    CatalogItemRestClient catalogItemRestClient;
    @Autowired
    SupplierRestClient supplierRestClient;
    @Autowired
    MaterialGroupRestClient materialGroupRestClient;

    @Autowired
    CatalogService catalogService;

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/details", method = RequestMethod.GET)
    public
    @ResponseBody
    CatalogItem getCatalogItemByCatalogIdItemId(HttpServletRequest request, HttpServletResponse response,
                                                @PathVariable("catalogid") String catalogid, @PathVariable("catalogitemid") String catalogitemid) {
        User user = (User) SessionDataRetriever.getLoggedInUser(request);
        
        CatalogItem item = new CatalogItem();
        populateCatalogItem(catalogItemRestClient.getCatalogItemByCatalogIdItemId(catalogid, catalogitemid), item);

        CatalogSummary catalogSummary = catalogService.getCatalog(catalogid);
        if (StringUtils.isEmpty(item.getSupplier())) {
            if (CatalogDataDisplayPopulator.isValidSupplierId(catalogSummary.getSupplierId())) {
                item.setSupplier(catalogSummary.getSupplierId());
            }
        }
        populateSuppliers(item, user.getUnitId());
        populateCatagories(item, user.getUnitId());
        populateCurrencies(item, user.getUnitId());

        int catalogState = (catalogSummary.getCatalogStateId() == 1 || catalogSummary.getCatalogStateId() == 3) ? 1 : 2;

        item.setReadOnly(catalogState + "");

        //ie issues.. caching .. backbone calling is failing..
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, proxy-revalidate, no-transform, pre-check=0, post-check=0, private");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        
     /*   String price =item.getPrice();
        String listPrice =item.getListPrice();
        String tieredPricing= item.getTieredPricing();
        
        if(price!= null && !price.isEmpty())
        item.setPrice(DecimalNotation.convertDecimalNotation(Double.parseDouble(price), Integer.parseInt(user.getDecimalNotation())));
        
        if(listPrice!= null && !listPrice.isEmpty())
          item.setListPrice(DecimalNotation.convertDecimalNotation(Double.parseDouble(listPrice), Integer.parseInt(user.getDecimalNotation())));
        
        if(tieredPricing != null && !tieredPricing.isEmpty())
          item.setTieredPricing(DecimalNotation.convertTieredPriceingDecimalNotation(tieredPricing, Integer.parseInt(user.getDecimalNotation())));*/
        
        return item;
    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/details", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void updateCatalogItem(@PathVariable("catalogid") String catalogid, @PathVariable("catalogitemid") String catalogitemid, @RequestBody CatalogItem itemProxy) {
        try {
            OciCatalogItemProxy proxy = new OciCatalogItemProxy();
            populateCatalogItemProxy(proxy, itemProxy);
            catalogItemRestClient.updateCatalogItem(catalogid, catalogitemid, proxy);
        } catch (Exception e) {
            LOGGER.error("updateCatalogItem:", e);
//            String errorMsg = e.getMessage();
            //return "FAILURE:" + (errorMsg.substring(errorMsg.lastIndexOf(":") + 1));
        }
        //return "SUCCESS:Update completed";
    }


    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/tiered-prices", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TieredPrice> getCatalogItemTieredPrices(HttpServletRequest request, HttpServletResponse response,
                                                 @PathVariable("catalogid") String catalogid, @PathVariable("catalogitemid") String catalogitemid) {
        //CatalogItem item = new CatalogItem();
        //populateCatalogItem(catalogItemRestClient.getCatalogItemByCatalogIdItemId(catalogid,catalogitemid),item);
        return catalogItemRestClient.getCatalogItemTieredPrices(catalogid, catalogitemid);
        //return  item.getTieredPrices();

    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/tiered-prices", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void updateCatalogItemTieredPrices(@PathVariable("catalogid") String catalogid, @PathVariable("catalogitemid") String catalogitemid, @RequestBody TieredPrice tieredPrice) {
        try {

//            OciCatalogItemProxy proxy = catalogItemRestClient.getCatalogItemByCatalogIdItemId(catalogid,catalogitemid);
//
//            if(proxy.getTieredPrices()==null){
//                proxy.setTieredPrices(new ArrayList<TieredPrice>());
//            }
//
//            if(tieredPrice.getId()==null || "".equals(tieredPrice.getId())){
//                tieredPrice.setId(new RandomString(10).nextString());
//
//                proxy.getTieredPrices().add(tieredPrice);
//            }else{
//                List<TieredPrice> list =proxy.getTieredPrices();
//                for(TieredPrice tieredPrice1: list){
//                    if(tieredPrice.getId().equalsIgnoreCase(tieredPrice1.getId())){
//                        tieredPrice.setPrice(tieredPrice1.getPrice());
//                        tieredPrice.setFrom(tieredPrice1.getFrom());
//                        tieredPrice.setTo(tieredPrice1.getTo());
//                    }
//                }
//                proxy.setTieredPrices(list);
//            }
            //    catalogItemRestClient.updateCatalogItem(catalogid, catalogitemid, proxy);
            catalogItemRestClient.updateCatalogItemTieredPrice(catalogid, catalogitemid, tieredPrice);
        } catch (Exception e) {
            LOGGER.error("updateCatalogItem:", e);
//            String errorMsg = e.getMessage();
            //return "FAILURE:" + (errorMsg.substring(errorMsg.lastIndexOf(":") + 1));
        }
        //return "SUCCESS:Update completed";
    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/tiered-prices", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deleteCatalogItemTieredPrices(@PathVariable("catalogid") String catalogid, @PathVariable("catalogitemid") String catalogitemid, @RequestBody TieredPrice tieredPrice) {
        try {

            List<TieredPrice> newList = new ArrayList<TieredPrice>();
            OciCatalogItemProxy proxy = catalogItemRestClient.getCatalogItemByCatalogIdItemId(catalogid, catalogitemid);
            List<TieredPrice> list = proxy.getTieredPrices();
            for (TieredPrice tieredPrice1 : list) {
                if (!tieredPrice.getId().equalsIgnoreCase(tieredPrice1.getId())) {
                    newList.add(tieredPrice1);
                }
            }
            proxy.setTieredPrices(newList);
            catalogItemRestClient.updateCatalogItem(catalogid, catalogitemid, proxy);
        } catch (Exception e) {
            LOGGER.error("updateCatalogItem:", e);
//            String errorMsg = e.getMessage();
            //return "FAILURE:" + (errorMsg.substring(errorMsg.lastIndexOf(":") + 1));
        }
        //return "SUCCESS:Update completed";
    }


//    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/tiered-prices/{tieredpricesid}",  method = RequestMethod.POST )
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    public void updateCatalogItemTieredPrices(@PathVariable("catalogid")String catalogid, @PathVariable("catalogitemid")String catalogitemid, @RequestBody TieredPrice tieredPrice){
//        try{
//
//            OciCatalogItemProxy proxy = catalogItemRestClient.getCatalogItemByCatalogIdItemId(catalogid,catalogitemid);
//            tieredPrice.setId(new RandomString(10).nextString());
//            proxy.getTieredPrices().add(tieredPrice);
//
//
//            catalogItemRestClient.updateCatalogItem(catalogid,catalogitemid,proxy);
//        }catch (Exception e){
//            LOGGER.error("updateCatalogItem:",e);
//            String errorMsg = e.getMessage();
//            //return "FAILURE:" + (errorMsg.substring(errorMsg.lastIndexOf(":") + 1));
//        }
//        //return "SUCCESS:Update completed";
//    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/attachments", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ImageAttachmentResource> getCatalogItemAttachments(HttpServletRequest request, HttpServletResponse response,
                                                            @PathVariable("catalogid") String catalogid, @PathVariable("catalogitemid") String catalogitemid) {
        return catalogItemRestClient.getCatalogItemAttachments(catalogid, catalogitemid);
    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/attachments/{attachmentid}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void updateItemAttachment(@PathVariable("catalogid") String catalogid, @PathVariable("catalogitemid") String catalogitemid, @PathVariable("attachmentid") String attachmentid, @RequestBody FileResourceForm fileForm) {
        try {

            ImageAttachmentResource imageAttachmentResource = new ImageAttachmentResource();
            imageAttachmentResource.setFileResourceId(attachmentid);

            if (fileForm.getFileTitle() != null && !"".equals(fileForm.getFileTitle().trim()))
                imageAttachmentResource.setFileName(fileForm.getFileTitle());

            if (fileForm.getFileTag() != null && !"".equals(fileForm.getFileTag().trim()))
                imageAttachmentResource.setFileTag(fileForm.getFileTag());

            catalogItemRestClient.updateCatalogItemAttachments(catalogid, catalogitemid, imageAttachmentResource);
        } catch (Exception e) {
            LOGGER.error("updateCatalogItem:", e);
//            String errorMsg = e.getMessage();
            //return "FAILURE:" + (errorMsg.substring(errorMsg.lastIndexOf(":") + 1));
        }
        //return "SUCCESS:Update completed";
    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/attachments/{attachmentid}", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void removeItemAttachment(@PathVariable("catalogid") String catalogid, @PathVariable("catalogitemid") String catalogitemid, @PathVariable("attachmentid") String attachmentids) {
        try {
            catalogItemRestClient.removeCatalogItemAttachments(catalogid, catalogitemid, attachmentids);
        } catch (Exception e) {
            LOGGER.error("updateCatalogItem:", e);
//            String errorMsg = e.getMessage();
            // return "FAILURE:" + (errorMsg.substring(errorMsg.lastIndexOf(":") + 1));
        }
        //return "SUCCESS:Update completed";
    }


    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/images", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ImageAttachmentResource> getCatalogItemImages(HttpServletRequest request, HttpServletResponse response,
                                                       @PathVariable("catalogid") String catalogid, @PathVariable("catalogitemid") String catalogitemid) {
        return catalogItemRestClient.getCatalogItemImages(catalogid, catalogitemid);
    }


    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/images/{imageid}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void updateItemImage(@PathVariable("catalogid") String catalogid, @PathVariable("catalogitemid") String catalogitemid,
                                @PathVariable("imageid") String imageid, @RequestBody FileResourceForm fileForm) {
        try {

            ImageAttachmentResource imageAttachmentResource = new ImageAttachmentResource();
            imageAttachmentResource.setFileResourceId(imageid);

            if ((fileForm.getFileTitle() != null && !"".equals(fileForm.getFileTitle().trim())) || (fileForm.getFileName() != null && !"".equals(fileForm.getFileName().trim())))
                imageAttachmentResource.setFileName(fileForm.getFileTitle());

            if (fileForm.getFileTag() != null && !"".equals(fileForm.getFileTag().trim()))
                imageAttachmentResource.setFileTag(fileForm.getFileTag());

            catalogItemRestClient.updateCatalogItemImages(catalogid, catalogitemid, imageAttachmentResource);
        } catch (Exception e) {
            LOGGER.error("updateCatalogItem:", e);
//            String errorMsg = e.getMessage();
            //return "FAILURE:" + (errorMsg.substring(errorMsg.lastIndexOf(":") + 1));
        }
        //return "SUCCESS:Update completed";
    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/images/{imageids}", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void removeItemImage(@PathVariable("catalogid") String catalogid, @PathVariable("catalogitemid") String catalogitemid, @PathVariable("imageids") String imageids) {
        try {
            catalogItemRestClient.removeCatalogItemImages(catalogid, catalogitemid, imageids);
        } catch (Exception e) {
            LOGGER.error("updateCatalogItem:", e);
//            String errorMsg = e.getMessage();
            //  return "FAILURE:" + (errorMsg.substring(errorMsg.lastIndexOf(":") + 1));
        }
        //return "SUCCESS:Update completed";
    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String createCatalogItem(@PathVariable("catalogid") String catalogid, @RequestBody OciCatalogItemProxy requestBody) {
        return catalogItemRestClient.createCatalogItem(catalogid, requestBody);
    }

    @RequestMapping("/itemdetail")
    public String catalogDetailPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        //ie issues.. caching .. backbone calling is failing..
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, proxy-revalidate, no-transform, pre-check=0, post-check=0, private");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        return "item_detail";
    }


    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/customfields/paginated", method = RequestMethod.GET)
    @ResponseBody
    public Page<CustomFieldValue> getCatalogItemCustomFields(HttpServletRequest request, @PathVariable String catalogid,
                                                             @PathVariable String catalogitemid,
                                                             @RequestParam("pageNumber") Integer pageNumber) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        int pageSize = user.getRowsPerPage();

        List<CustomFieldValue> list = getCatalogItemCustomFields(catalogid, catalogitemid);

        int fromIndex = (pageNumber - 1) * pageSize;
        int toIndex = fromIndex + pageSize;
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        List<CustomFieldValue> sublist = list.subList(fromIndex, toIndex);
        Page<CustomFieldValue> customFieldPage = new Page<CustomFieldValue>(sublist, pageNumber, pageSize, list.size());

        return customFieldPage;
    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/customfields", method = RequestMethod.GET)
    @ResponseBody
    public List<CustomFieldValue> getCatalogItemCustomFields(@PathVariable String catalogid,
                                                             @PathVariable String catalogitemid) {
        List<CustomFieldValue> list = new ArrayList<CustomFieldValue>();
        try {
            OciCatalogItemProxy itemProxy = catalogItemRestClient.getCatalogItemByCatalogIdItemId(catalogid, catalogitemid);
            List<OciItemCustomFieldValue> tempList = itemProxy.getCustomFieldItems();
            Map<String, CatalogCustomField> catalogCustomFieldMap = catalogService.getCatalogCustomFieldByCatalogId(itemProxy.getCatalogId());

            if (tempList != null) {
                for (OciItemCustomFieldValue resource : tempList) {
                    CatalogCustomField catalogCustomField = catalogCustomFieldMap.get(resource.getCustomFieldId());
                    if (!resource.isDeleted() && catalogCustomField != null) {
                        CustomFieldValue cv = new CustomFieldValue();
                        cv.setActive(resource.isActive());
                        cv.setCustomFieldId(resource.getCustomFieldId());
                        cv.setDisplayOrder(catalogCustomField.getDisplayOrder());
                        //cv.setDescription(resource.);
                        //cv.setDisplayName();
                        cv.setFieldName(resource.getFieldName());
                        cv.setFieldType(resource.getFieldType());
                        if (resource.getFieldType() != null && resource.getFieldType().length() > 0 && resource.getFieldType().equalsIgnoreCase("list")) {
                            if (resource.getValue() != null && resource.getValue().length() > 0) {
                                String[] options = resource.getValue().split(";");
                                if (options.length > 0)
                                    cv.setOptions(options);
                                else
                                    cv.setOptions(null);
                            }

                        } else {
                            cv.setValue(resource.getValue());
                        }
                        list.add(cv);
                    }
                }
            }
            if (list.size() > 0) {
                BinaryInsertSortCustomFieldsOnDisplayOrder.sort(list, list.size());
            }
            // customFieldValueList = catalogService.getCatalogCustomFieldByCatalogId(catalogid);
        } catch (Exception exp) {
            exp.printStackTrace();
            LOGGER.error("Error retrieving CustomField for UnitId(" + catalogid + ") ", exp);
        }
        return list;
    }


    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/customfields/{customfieldids}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCatalogItemCustomField(@PathVariable String catalogid,
                                             @PathVariable String catalogitemid, @PathVariable String customfieldids, @RequestParam String status) {
        try {
            if (status != null && STATUS_ACTIVATE.equals(status)) {
                catalogItemRestClient.activateCatalogItemCustomFields(catalogid, catalogitemid, customfieldids);

            } else if (status != null && STATUS_DEACTIVATE.equals(status)) {
                catalogItemRestClient.deActivateCatalogItemCustomFields(catalogid, catalogitemid, customfieldids);
            } else {
                // todo
                return;
            }

        } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
        }
    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/customfields/{customfieldids}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCatalogItemCustomField(@PathVariable String catalogid,
                                             @PathVariable String catalogitemid, @PathVariable String customfieldids) {
        try {
            catalogItemRestClient.deleteCatalogItemCustomFields(catalogid, catalogitemid, customfieldids);
        } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
        }
    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{catalogitemid}/customfields", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void updateCatalogItemCustomField(@PathVariable String catalogid,
                                             @PathVariable String catalogitemid, @RequestBody String[] customfieldids) {
        try {
            catalogItemRestClient.addCatalogItemCustomFields(catalogid, catalogitemid, StringUtils.join(customfieldids, ","));

        } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
        }
    }

    @RequestMapping(value = "/getItemCustomFields", method = RequestMethod.POST)
    public String getCustomFields(HttpServletRequest request, @RequestParam String catalogId, @RequestParam(value = "catalogItemId", required = false) String catalogItemId, @RequestParam(value = "pageNumber", required = false) Integer pageNumber, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        try {
            Map<String, CatalogCustomField> catalogCustomFieldMap = catalogService.getCatalogCustomFieldByCatalogId(catalogId);

            if (pageNumber == null) {
                pageNumber = 1;
            }
            int pageSize = user.getRowsPerPage();

            if (catalogItemId != null && catalogItemId.length() > 0) {
                OciCatalogItemProxy itemProxy = catalogItemRestClient.getCatalogItemByCatalogIdItemId(catalogId, catalogItemId);
                List<OciItemCustomFieldValue> tempList = itemProxy.getCustomFieldItems();
                if (tempList != null) {
                    for (OciItemCustomFieldValue resource : tempList) {
                        if (!resource.isDeleted()) {
                            catalogCustomFieldMap.remove(resource.getCustomFieldId());
                        }
                    }
                }
            }
            List<CatalogCustomField> customFields = new ArrayList<CatalogCustomField>(catalogCustomFieldMap.values());
            int offset = pageSize * (pageNumber - 1);
            int maxCount = (customFields.size() > pageSize + offset) ? pageSize + offset : customFields.size();
            List<CatalogCustomField> pageItems = customFields.subList(offset, maxCount);

            Page<CatalogCustomField> customFieldsPage = new Page<CatalogCustomField>(pageItems, pageNumber, pageSize, customFields.size());

            modelMap.addAttribute("customFieldsPage", customFieldsPage);
            modelMap.addAttribute("catalogId", catalogId);
            modelMap.addAttribute("catalogItemId", catalogItemId);

        } catch (Exception exception) {
            LOGGER.error("Error in adding custom fields", exception);
            return FAILURE;
        }
        return ASS_ITEM_CUSTOM_FIELD_SECTION;
    }

    private void populateSuppliers(CatalogItem item, String unitId) {
        try {

            List<KeyValue> suppliers = new ArrayList<KeyValue>();
            List<Supplier> supplierCompanyList = supplierRestClient.getActiveSupplierByUnitId(unitId);
            for (Supplier supplier : supplierCompanyList) {
                KeyValue supplierui = new KeyValue();
                supplierui.setId(supplier.getCompanyId());
                supplierui.setName(supplier.getCompanyName());
                suppliers.add(supplierui);
            }
            item.setSuppliers(suppliers);
        } catch (Exception e) {
            //TODO adding  print stack trace for now since we need to handle this rest call which is failing
            LOGGER.error("Exception occurred while trying to fetch suppliers from user data service." + e);
        }
    }

    private void populateCatagories(CatalogItem item, String unitId) {
        try {
/*            List<KeyValue> groups =  new ArrayList<KeyValue>();
            List<MaterialGroupProxy> materialGroupList = materialGroupRestClient.fetchMaterialGroups(unitId);
            for (MaterialGroupProxy materialGroup:materialGroupList){
                KeyValue group = new KeyValue();
                group.setId(materialGroup.getCompanyCategoryCode());
                group.setName(materialGroup.getCompanyLabel());
                groups.add(group);
            }
            // todo for alfie testing only-- removed this code after alfie devlopment is done
            if(groups.size()<=0){
                KeyValue testGroup = new KeyValue();
                testGroup.setId("12345");
                testGroup.setName("Tables");

                KeyValue testGroup1 = new KeyValue();
                testGroup1.setId("23456");
                testGroup1.setName("Coputers");
                groups.add(testGroup);
                groups.add(testGroup1);
            }

                //
            item.setCatagories(groups);*/
        } catch (Exception e) {
            //TODO adding  print stack trace for now since we need to handle this rest call which is failing
            LOGGER.error("Exception occurred while trying to fetch suppliers from user data service." + e);
        }
    }

    private void populateCurrencies(CatalogItem item, String unitId) {
        try {
            List<KeyValue> currencies = new ArrayList<KeyValue>();
            List<CurrencyCode> currencyCodeList = supplierRestClient.getAllCurrencyCodes();
            for (CurrencyCode currencyCode : currencyCodeList) {
                KeyValue group = new KeyValue();
                group.setId(currencyCode.getCode());
                group.setName(currencyCode.getName());
                currencies.add(group);
            }
            item.setCurrencies(currencies);
        } catch (Exception e) {
            //TODO adding  print stack trace for now since we need to handle this rest call which is failing
            LOGGER.error("Exception occurred while trying to fetch suppliers from user data service." + e);
        }
    }


    private OciCatalogItemProxy populateCatalogItemProxy(OciCatalogItemProxy catalogItemProxy, CatalogItem catalogItem) {
        if (catalogItemProxy != null) {
            catalogItemProxy.setTieredPricing(catalogItem.getTieredPricing());
            catalogItemProxy.setNewItemMatgroup(catalogItem.getNewItemMatgroup());
            catalogItemProxy.setCatalogId(catalogItem.getCatalogId());
            catalogItemProxy.setCatalogItemId(catalogItem.getCatalogItemId());
            catalogItemProxy.setNewItemService(catalogItem.getItemType());
            catalogItemProxy.setNewItemDescription(catalogItem.getShortDescription());
            catalogItemProxy.setNewItemCurrency(catalogItem.getCurrency());
            catalogItemProxy.setPrice(catalogItem.getPrice());
            catalogItemProxy.setNewItemUnit(catalogItem.getUnitOfMeasure());
            catalogItemProxy.setNewItemVendormat(catalogItem.getVendorPartNumber());
            catalogItemProxy.setNewItemVendor(catalogItem.getSupplier());
            catalogItemProxy.setItemManufacturerName(catalogItem.getManufactName());
            catalogItemProxy.setNewItemManufactmat(catalogItem.getManufactPart());
            catalogItemProxy.setItemUrl(catalogItem.getItemUrl());
            catalogItemProxy.setNewItemContractItem(catalogItem.getContract());
            catalogItemProxy.setNewItemContractItem(catalogItem.getContractLineItem());
            catalogItemProxy.setConfigurable((catalogItem.getConfigurable() != null && "true".equals(catalogItem.getConfigurable())) ? 1 : 0);
            catalogItemProxy.setNewItemLongtext1(catalogItem.getLongDescription());
            catalogItemProxy.setOrderMultiplyQuantity(catalogItem.getOrderMultiplyQuantity());
            catalogItemProxy.setItemQtyOnhand(catalogItem.getQuantityOnHand());
            catalogItemProxy.setItemMinOrderQty(catalogItem.getMinimumOrderQuantity());
            catalogItemProxy.setNewItemContract(catalogItem.getContract());
            catalogItemProxy.setNewItemLeadtime(catalogItem.getLeadTime());
//            Fulfillment fulfillment = catalogItem.getFulfillment();
//            if(fulfillment!=null){
            catalogItemProxy.setItemInstock((catalogItem.getInStock() == true) ? "Y" : "N");
            catalogItemProxy.setItemInstock(catalogItem.getLeadTime());
            catalogItemProxy.setItemInstock(catalogItem.getMinimumOrderQuantity());
            catalogItemProxy.setItemInstock(catalogItem.getOrderMultiplyQuantity());
            catalogItemProxy.setItemInstock(catalogItem.getQuantityOnHand());
//            }

//            SapFields sapFields = catalogItem.getSapFields();
//            if(sapFields!=null){

            catalogItemProxy.setNewItemCustField1(catalogItem.getNewItemCustField1());
            catalogItemProxy.setNewItemCustField2(catalogItem.getNewItemCustField2());
            catalogItemProxy.setNewItemCustField3(catalogItem.getNewItemCustField3());
            catalogItemProxy.setNewItemCustField4(catalogItem.getNewItemCustField4());
            catalogItemProxy.setNewItemCustField5(catalogItem.getNewItemCustField5());
            catalogItemProxy.setNewItemMatnr(catalogItem.getNewItemMatnr());
            catalogItemProxy.setNewItemExtSchemaType(catalogItem.getNewItemExtSchemaType());
            catalogItemProxy.setNewItemExtCatagoryId(catalogItem.getNewItemExtCatagoryId());
            catalogItemProxy.setNewItemExtCatagory(catalogItem.getNewItemExtCatagory());
            catalogItemProxy.setNewItemSldSysName(catalogItem.getNewItemSldSysName());
            catalogItemProxy.setNewItemExtProductId(catalogItem.getNewItemExtProductId());
            catalogItemProxy.setItemConfig(catalogItem.getItemConfig());
            catalogItemProxy.setItemPolicy(catalogItem.isItemPolicy()? "X": "");
            catalogItemProxy.setItemSuppress(catalogItem.isItemSuppress() ? "X" : "");
            catalogItemProxy.setItemModelno(catalogItem.getModelno());
            catalogItemProxy.setItemBrandName(catalogItem.getBrandName());
            catalogItemProxy.setItemBundleNo(catalogItem.getItemBundleNo());
            catalogItemProxy.setBundleQuantity(catalogItem.getBundleQuantity());
            if (catalogItem.getItemOrderInterval() != null && catalogItem.getItemOrderInterval().length() > 0) {
                catalogItemProxy.setItemOrderInterval(catalogItem.getItemOrderInterval());
            }
            catalogItemProxy.setItemSuppressPrice(catalogItem.isItemSuppressPrice() ? "x" : "");  
//            }
//
//
//            Pricing pricing = catalogItem.getPricing();
//            if(sapFields!=null){
            catalogItemProxy.setItemListPrice(catalogItem.getListPrice());
            catalogItemProxy.setNewItemPriceunit(catalogItem.getPriceUnit());
            catalogItemProxy.setPriceEditable(catalogItem.isPriceEditable());
            if (catalogItem.getTieredPrices() != null && catalogItem.getTieredPrices().size() > 0) {
                catalogItemProxy.setTieredPrices(catalogItem.getTieredPrices());
            }

            catalogItemProxy.setNewItemExtQuoteItem(catalogItem.getNewItemExtQuoteItem());
//
//            }

        }
        return catalogItemProxy;
    }

    private CatalogItem populateCatalogItem(OciCatalogItemProxy catalogItemProxy, CatalogItem catalogItem) {
        if (catalogItemProxy != null) {

            catalogItem.setNewItemMatgroup(catalogItemProxy.getNewItemMatgroup() != null ? catalogItemProxy.getNewItemMatgroup() : "");


            catalogItem.setCatalogId(catalogItemProxy.getCatalogId());
            catalogItem.setCatalogItemId(catalogItemProxy.getCatalogItemId());
            catalogItem.setItemType(catalogItemProxy.getNewItemService() != null ? catalogItemProxy.getNewItemService() : "");
            catalogItem.setOrderMultiplyQuantity(catalogItemProxy.getOrderMultiplyQuantity() == null ? "" : catalogItemProxy.getOrderMultiplyQuantity());

            catalogItem.setShortDescription(catalogItemProxy.getNewItemDescription() != null ? catalogItemProxy.getNewItemDescription() : "");
            catalogItem.setShortDescription(catalogItem.getShortDescription().replaceAll("\"", "&quot;"));
            catalogItem.setCurrency(catalogItemProxy.getNewItemCurrency() != null ? catalogItemProxy.getNewItemCurrency() : "");
            catalogItem.setPrice(catalogItemProxy.getPrice() != null ? catalogItemProxy.getPrice() : "");
            catalogItem.setUnitOfMeasure(catalogItemProxy.getNewItemUnit() != null ? catalogItemProxy.getNewItemUnit() : "");
            catalogItem.setVendorPartNumber(catalogItemProxy.getNewItemVendormat() != null ? catalogItemProxy.getNewItemVendormat() : "");
            catalogItem.setSupplier(catalogItemProxy.getNewItemVendor() != null ? catalogItemProxy.getNewItemVendor() : "");
            //   catalogItem.setSupplier(catalogItemProxygetNewItemVendor()!=null?catalogItemProxy.getNewItemVendor():"");

            catalogItem.setManufactName(catalogItemProxy.getItemManufacturerName() != null ? catalogItemProxy.getItemManufacturerName() : "");
            catalogItem.setManufactPart(catalogItemProxy.getNewItemManufactmat() != null ? catalogItemProxy.getNewItemManufactmat() : "");
            catalogItem.setItemUrl(catalogItemProxy.getItemUrl() != null ? catalogItemProxy.getItemUrl() : "");
//            catalogItem.setContract(catalogItemProxy.getNewItemContractItem()!=null?catalogItemProxy.getNewItemContractItem():"");
            catalogItem.setContract(catalogItemProxy.getNewItemContract() != null ? catalogItemProxy.getNewItemContract() : "");
            catalogItem.setContractLineItem(catalogItemProxy.getNewItemContractItem() != null ? catalogItemProxy.getNewItemContractItem() : "");
            catalogItem.setConfigurable((catalogItemProxy.getConfigurable() != null && catalogItemProxy.getConfigurable() == 1) ? "true" : "false");
            catalogItem.setLongDescription(catalogItemProxy.getNewItemLongtext1() != null ? catalogItemProxy.getNewItemLongtext1() : "");
            catalogItem.setItemConfig((catalogItemProxy.getItemConfig() != null && (catalogItemProxy.getItemConfig().equals("1") || catalogItemProxy.getItemConfig().equals("true"))) ? "true" : "false");
            catalogItem.setItemPolicy((catalogItemProxy.getItemPolicy() != null && catalogItemProxy.getItemPolicy().length()>0 && !catalogItemProxy.getItemPolicy().equals("0") && !catalogItemProxy.getItemPolicy().equals("false")) ? true : false);
            //Fulfillment fulfillment = new Fulfillment();
            catalogItem.setInStock(catalogItemProxy.getItemInstock() != null ? true : false);
            catalogItem.setLeadTime(catalogItemProxy.getNewItemLeadtime() != null ? catalogItemProxy.getNewItemLeadtime() : "");
            catalogItem.setMinimumOrderQuantity(catalogItemProxy.getItemMinOrderQty() != null ? catalogItemProxy.getItemMinOrderQty() : "");
            catalogItem.setQuantityOnHand(catalogItemProxy.getItemQtyOnhand() != null ? catalogItemProxy.getItemQtyOnhand() : "");
            // catalogItem.setFulfillment(fulfillment);

            //SapFields sapFields = new SapFields();
            catalogItem.setNewItemCustField1(catalogItemProxy.getNewItemCustField1() != null ? catalogItemProxy.getNewItemCustField1() : "");
            catalogItem.setNewItemCustField2(catalogItemProxy.getNewItemCustField2() != null ? catalogItemProxy.getNewItemCustField2() : "");
            catalogItem.setNewItemCustField3(catalogItemProxy.getNewItemCustField3() != null ? catalogItemProxy.getNewItemCustField3() : "");
            catalogItem.setNewItemCustField4(catalogItemProxy.getNewItemCustField4() != null ? catalogItemProxy.getNewItemCustField4() : "");
            catalogItem.setNewItemCustField5(catalogItemProxy.getNewItemCustField5() != null ? catalogItemProxy.getNewItemCustField5() : "");
            catalogItem.setNewItemMatnr(catalogItemProxy.getNewItemMatnr() != null ? catalogItemProxy.getNewItemMatnr() : "");
            catalogItem.setNewItemExtSchemaType(catalogItemProxy.getNewItemExtSchemaType() != null ? catalogItemProxy.getNewItemExtSchemaType() : "");
            catalogItem.setNewItemExtCatagoryId(catalogItemProxy.getNewItemExtCatagoryId() != null ? catalogItemProxy.getNewItemExtCatagoryId() : "");
            catalogItem.setNewItemExtCatagory(catalogItemProxy.getNewItemExtCatagory() != null ? catalogItemProxy.getNewItemExtCatagory() : "");
            catalogItem.setNewItemSldSysName(catalogItemProxy.getNewItemSldSysName() != null ? catalogItemProxy.getNewItemSldSysName() : "");
            catalogItem.setNewItemExtProductId(catalogItemProxy.getNewItemExtProductId() != null ? catalogItemProxy.getNewItemExtProductId() : "");
            catalogItem.setNewItemExtQuoteItem(catalogItemProxy.getNewItemExtQuoteItem() != null ? catalogItemProxy.getNewItemExtQuoteItem() : "");
            //catalogItem.setSapFields(sapFields);

            //Pricing pricing = new Pricing();
            catalogItem.setListPrice(catalogItemProxy.getItemListPrice() != null ? catalogItemProxy.getItemListPrice() : "");
            catalogItem.setPriceUnit(catalogItemProxy.getNewItemPriceunit() != null ? catalogItemProxy.getNewItemPriceunit() : "");
            catalogItem.setPriceEditable(true);

            // To DO for alfie test only.
            List<TieredPrice> testTieredPrices = new ArrayList<TieredPrice>();
            TieredPrice tieredPrice = new TieredPrice();
            tieredPrice.setFrom(1);
            tieredPrice.setTo(5);
            tieredPrice.setPrice("$50.00");
            testTieredPrices.add(tieredPrice);


            catalogItem.setTieredPrices(catalogItemProxy.getTieredPrices() != null ? catalogItemProxy.getTieredPrices() : testTieredPrices);
            catalogItem.setTieredPricing(catalogItemProxy.getTieredPricing() != null ? catalogItemProxy.getTieredPricing() : "");

            catalogItem.setModelno(catalogItemProxy.getItemModelno() != null ? catalogItemProxy.getItemModelno() : "");
            catalogItem.setBrandName(catalogItemProxy.getItemBrandName() != null ? catalogItemProxy.getItemBrandName() : "");
            catalogItem.setItemOrderInterval(catalogItemProxy.getItemOrderInterval() != null ? catalogItemProxy.getItemOrderInterval() : "");
            catalogItem.setItemBundleNo(catalogItemProxy.getItemBundleNo() != null ? catalogItemProxy.getItemBundleNo() : "");
            catalogItem.setBundleQuantity(catalogItemProxy.getBundleQuantity() != null ? catalogItemProxy.getBundleQuantity() : "");
            catalogItem.setItemSuppress(catalogItemProxy.getItemSuppress()!=null && catalogItemProxy.getItemSuppress().toLowerCase().equals("x") ? true : false);
            catalogItem.setItemSuppressPrice(StringUtils.isNotBlank(catalogItemProxy
                .getItemSuppressPrice())
                && catalogItemProxy.getItemSuppressPrice().toLowerCase().equals("x") ? true
                     : false);
        }
        return catalogItem;
    }


    //Verify if any one is using this uri

    @RequestMapping(value = "/organization/{organizationid}/catalogitem/{catalogitemid}", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    OciCatalogItemProxy getCatalogItemByUnitIdItemId(@PathVariable("organizationid") String organizationid, @PathVariable("catalogitemid") String catalogitemid) {
        return catalogItemRestClient.getCatalogItemByUnitIdItemId(organizationid, catalogitemid);
    }

    @RequestMapping(value = "/catalogs/{catalogid}/catalogitems/{itemBundleNo}/{unitId}", method = RequestMethod.GET)
    public
    @ResponseBody
    String findCatalogByBundleNameAndUnitId(HttpServletRequest request, HttpServletResponse response,
                                            @PathVariable("catalogid") String catalogid, @PathVariable("itemBundleNo") String itemBundleNo, @PathVariable("unitId") String unitId) {
        try {

            if (!itemBundleNo.equals("")) {

                long count = catalogItemRestClient.findCatalogByBundleNameAndUnitId(catalogid, itemBundleNo, unitId);
                if (count > 0) {
                    return "Bundle Name should not already exist in the same company";
                }
            }

        } catch (Exception e) {
            LOGGER.error("updateCatalogItem:", e);
//            String errorMsg = e.getMessage();
            //return "FAILURE:" + (errorMsg.substring(errorMsg.lastIndexOf(":") + 1));
        }
        return "";
    }


}
