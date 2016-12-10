package com.vroozi.customerui.matgroup.services;

import java.util.List;

import com.vroozi.customerui.materialgroup.model.MaterialGroupStateProxy;
import com.vroozi.customerui.supplier.model.VendorMapping;
import com.vroozi.customerui.supplier.model.VendorMappingStateProxy;
import com.vroozi.customerui.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vroozi.customerui.catalog.model.MaterialGroupPost;
import com.vroozi.customerui.materialgroup.model.MaterialGroupProxy;
import com.vroozi.customerui.matgroup.services.rest.MaterialGroupRestClient;

@Service
public class MaterialGroupServiceImpl implements MaterialGroupService{
    @Autowired
    private MaterialGroupRestClient matGroupRestClient;
    
    public MaterialGroupServiceImpl() {
		// TODO Auto-generated constructor stub
	}
    
    @Override
    public void uploadMaterialGroup(MaterialGroupPost materialGroupPost)
    		throws Exception {
    	matGroupRestClient.uploadMaterialGroup(materialGroupPost);
    	
    }
    
    @Override
    public List<MaterialGroupProxy> findMaterialGroups(String unitId) throws Exception {
    	// TODO Auto-generated method stub
    	return matGroupRestClient.fetchMaterialGroups(unitId);
    }

    @Override
    public Page<MaterialGroupProxy> getPagedDataForMaterialGroupMapping(String unitId, int pageNumber, int pageSize, String searchTerm) {
          return matGroupRestClient.fetchPagedDataForMaterialGroupMappings(unitId, pageNumber, pageSize, searchTerm);
    }

    @Override
    public MaterialGroupStateProxy getCurrentStateOfMaterialGroupMapping(String companyCode) throws Exception {
        return matGroupRestClient.getCurrentStateOfMaterialGroupMapping(companyCode);
    }

    @Override
    public boolean checkErrorReportExistsOfMaterialGroupMapping(String companyCode) throws Exception {
        return matGroupRestClient.checkErrorReportExistsOfMaterialGroupMapping(companyCode);
    }

    @Override
    public void uploadSupplierMapping(MaterialGroupPost materialGroupPost) throws Exception {
        matGroupRestClient.uploadSupplierMapping(materialGroupPost);
    }

    @Override
    public Page<VendorMapping> getPagedDataForCustomSupplierMapping(int unitId, int pageNo, int pageSize, String searchTerm, String uniqueSupplierId, List<String> supplierIds) throws Exception {
        return matGroupRestClient.fetchPagedDataForCustomSupplierMappings(unitId, pageSize, pageNo, searchTerm, uniqueSupplierId, supplierIds);
    }


    @Override
    public VendorMappingStateProxy getCurrentStateOfSupplierMapping(String companyCode) throws Exception {
        return matGroupRestClient.getCurrentStateOfSupplierMapping(companyCode);
    }
    
    @Override
    public void downloadSupplierMappings(String unitid , String path, List<String> supplierIds) throws Exception{
    	matGroupRestClient.downloadSupplierMapping(unitid,path, supplierIds);
    }
    
}
