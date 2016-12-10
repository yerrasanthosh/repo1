package com.vroozi.customerui.matgroup.services;

import java.util.List;

import com.vroozi.customerui.catalog.model.MaterialGroupPost;
import com.vroozi.customerui.materialgroup.model.MaterialGroupProxy;
import com.vroozi.customerui.materialgroup.model.MaterialGroupStateProxy;
import com.vroozi.customerui.supplier.model.VendorMapping;
import com.vroozi.customerui.supplier.model.VendorMappingStateProxy;
import com.vroozi.customerui.util.Page;

public interface MaterialGroupService {
	void uploadMaterialGroup(MaterialGroupPost createCatalogPost) throws Exception;
	List<MaterialGroupProxy> findMaterialGroups(String unitId) throws Exception;

    Page<MaterialGroupProxy> getPagedDataForMaterialGroupMapping(String unitId, int pageNumber, int pageSize, String searchTerm);

    MaterialGroupStateProxy getCurrentStateOfMaterialGroupMapping(String companyCode) throws Exception;

    boolean checkErrorReportExistsOfMaterialGroupMapping(String companyCode) throws Exception;

    void uploadSupplierMapping(MaterialGroupPost materialGroupPost) throws Exception;

    Page<VendorMapping> getPagedDataForCustomSupplierMapping(int unitId, int pageNo, int pageSize, String searchTerm, String uniqueSupplierId , List<String> supplierIds) throws Exception;

    VendorMappingStateProxy getCurrentStateOfSupplierMapping(String companyCode) throws Exception;
    
    void downloadSupplierMappings(String unitid,String path, List<String> supplierIds)throws Exception; 
    
}
