package com.vroozi.customerui.catalog.common;

import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.services.SupplierService;
import com.vroozi.customerui.user.services.UserManagementService;
import com.vroozi.customerui.user.services.user.model.User;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Provides common functions for both CatalogSummary page and CatalogDetail page.
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 9/14/12:1:52 PM
 */
@Component
public class CatalogDataDisplayPopulator {

    private final Logger logger = LoggerFactory.getLogger(CatalogDataDisplayPopulator.class);

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    SupplierService supplierService;



    public void populateCatalogDisplayNames(CatalogSummary catalogSummary){
        try{
          
            if(CatalogDataDisplayPopulator.isValidSupplierId(catalogSummary.getSupplierId())){
                Supplier supplier = supplierService.getSupplier(catalogSummary.getUnitId(),catalogSummary.getSupplierId());
                catalogSummary.setSupplierName(supplier!=null?supplier.getCompanyName():" ");
            }else{
                catalogSummary.setSupplierName(" ");
            }
        }catch (Exception e){
            //todo fix this one -- nullpointer is coming..
            logger.error("GET_SUPPLIER:",e);
        }

        try{
            if(catalogSummary.getSubmitterId()!=null && !"".equalsIgnoreCase(catalogSummary.getSubmitterId().trim())){
                User catalogCreater = userManagementService.getUser(catalogSummary.getSubmitterId());
                catalogSummary.setCreatorName(catalogCreater!=null?catalogCreater.getFirstName()+" "+catalogCreater.getLastName():" ");
            }else{
                catalogSummary.setCreatorName(" ");
            }

        }catch (Exception e){
            //todo fix this one -- nullpointer is coming..
            logger.error("GET_CATALOG_SUBMITTER_NAME:",e);
        }
        try{
            if(catalogSummary.getLastUpdatedBy()!=null && !"".equalsIgnoreCase(catalogSummary.getLastUpdatedBy().trim())){
                User catalogUpdator = userManagementService.getUser(catalogSummary.getLastUpdatedBy());
                catalogSummary.setUpdatorName(catalogUpdator!=null?catalogUpdator.getFirstName()+" "+catalogUpdator.getLastName():" ");
            }else{
                catalogSummary.setUpdatorName(" ");
            }
        }catch (Exception e){
            //todo fix this one -- nullpointer is coming..
            logger.error("GET_CATALOG_UPDATER_NAME:",e);
        }
    }
    
    public static boolean isValidSupplierId(String supplierId) {
      if (StringUtils.isBlank(supplierId) || supplierId.contains(",") || "0".equals(supplierId)) {
        return false;
      }
      return true;
    }
}
