package com.vroozi.customerui.matgroup.services.rest;

import java.util.List;

import com.vroozi.customerui.catalog.model.MaterialGroupPost;
import com.vroozi.customerui.materialgroup.model.MaterialGroupProxy;
import com.vroozi.customerui.materialgroup.model.MaterialGroupStateProxy;
import com.vroozi.customerui.supplier.model.VendorMapping;
import com.vroozi.customerui.supplier.model.VendorMappingStateProxy;
import com.vroozi.customerui.util.Page;

public interface MaterialGroupRestClient {
	void uploadMaterialGroup(MaterialGroupPost materialGroupPost) throws Exception;
	List<MaterialGroupProxy> fetchMaterialGroups(String unitId)throws Exception;

    Page<MaterialGroupProxy> fetchPagedDataForMaterialGroupMappings(String unitId, int pageNumber, int pageSize, String searchTerm);

    MaterialGroupStateProxy getCurrentStateOfMaterialGroupMapping(String companyCode) throws Exception;

    boolean checkErrorReportExistsOfMaterialGroupMapping(String companyCode) throws Exception;

    void uploadSupplierMapping(MaterialGroupPost materialGroupPost) throws Exception;

    Page<VendorMapping> fetchPagedDataForCustomSupplierMappings(int unitId, int pageSize, int pageNo, String searchTerm, String uniqueSupplierId, List<String> supplierIds) throws Exception;
  
    VendorMappingStateProxy getCurrentStateOfSupplierMapping(String companyCode) throws Exception;
   
    void downloadSupplierMapping(String unitid,String filename, List<String> supplierIds)throws Exception;
}
