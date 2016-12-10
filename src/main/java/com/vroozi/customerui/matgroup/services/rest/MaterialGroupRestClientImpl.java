package com.vroozi.customerui.matgroup.services.rest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vroozi.customerui.materialgroup.model.CustomSupplierMappingParams;
import com.vroozi.customerui.materialgroup.model.MaterialGroupStateProxy;
import com.vroozi.customerui.supplier.model.VendorMapping;
import com.vroozi.customerui.supplier.model.VendorMappingStateProxy;
import com.vroozi.customerui.util.Page;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vroozi.customerui.catalog.model.MaterialGroupPost;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.materialgroup.model.MaterialGroupProxy;
import com.vroozi.customerui.util.RestServiceUrl;

@Component
public class MaterialGroupRestClientImpl implements MaterialGroupRestClient{
	
    @Autowired
    RestServiceUrl restServiceUrl;
	
	@Override
	public void uploadMaterialGroup(MaterialGroupPost materialGroupPost)
			throws Exception {
        try{
            new RestTemplate().postForLocation(restServiceUrl.getUploadMaterialGroupURI(), materialGroupPost);
        }catch (Exception exp) {
            throw new AdminUIException(exp);
        }

		
	}
	
	@Override
	public List<MaterialGroupProxy> fetchMaterialGroups(String unitId)
			throws Exception {
	
		List<MaterialGroupProxy> list=(List<MaterialGroupProxy>)new RestTemplate().getForObject(restServiceUrl.getFindMaterialGroupURI()+"/unitId/"+unitId, List.class);
		return list;
	}

    @Override
    public Page<MaterialGroupProxy> fetchPagedDataForMaterialGroupMappings(String unitId, int pageNumber, int pageSize, String searchTerm) {

        Long count = new RestTemplate().getForObject(restServiceUrl.getCountMaterialGroupMappingsURI(), Long.class, unitId, searchTerm);
        List<MaterialGroupProxy> list=(List<MaterialGroupProxy>)new RestTemplate().getForObject(restServiceUrl.getFindMaterialGroupMappingPaginationURI(), List.class, unitId, pageNumber, pageSize, searchTerm);

        Page<MaterialGroupProxy> page = new Page<MaterialGroupProxy>();
        page.setPageItems(list);
        page.setPageNumber(pageNumber);
        page.setTotalRecords(count);
        long pageCount = count / pageSize;
        if (count > pageSize * pageCount) {
            pageCount++;
        }
        page.setPagesAvailable((int)pageCount);
        return page;

    }

    @Override
    public MaterialGroupStateProxy getCurrentStateOfMaterialGroupMapping(String companyCode) throws Exception {
        return new RestTemplate().getForObject(restServiceUrl.getMaterialGroupStatusURI(), MaterialGroupStateProxy.class, companyCode);
    }

    @Override
    public boolean checkErrorReportExistsOfMaterialGroupMapping(String companyCode) throws Exception {
        return new RestTemplate().getForObject(restServiceUrl.getMaterialGroupErrorStatusURI(), Boolean.class, companyCode);
    }

    @Override
    public void uploadSupplierMapping(MaterialGroupPost materialGroupPost) throws Exception {
        try{
            new RestTemplate().postForLocation(restServiceUrl.getUploadSupplierMappingURI(), materialGroupPost);
        }catch (Exception exp) {
            throw new AdminUIException(exp);
        }
    }

    @Override
    public Page<VendorMapping> fetchPagedDataForCustomSupplierMappings(int unitId, int pageSize, int pageNo, String searchTerm, String uniqueSupplierId,List<String> supplierIds) throws Exception {
        
    	CustomSupplierMappingParams params = new CustomSupplierMappingParams();
    	params.setUnitId(unitId);
    	params.setPageNo(pageNo);
    	params.setPageSize(pageSize);
    	params.setSearchTerm(searchTerm);
    	params.setUniqueSupplierId(uniqueSupplierId);
    	params.setSupplierIds(supplierIds);
    	
    	Long count = new RestTemplate().postForObject(restServiceUrl.getCountSupplierMappingsURI(),params, Long.class);
    	
    	VendorMapping[] vm=new RestTemplate().postForObject(restServiceUrl.getSupplierMappingPaginationURI(),params, VendorMapping[].class);    	
    	List<VendorMapping> list= Arrays.asList(vm);
   

        Page<VendorMapping> page = new Page<VendorMapping>();
        page.setPageItems(list);
        page.setPageNumber(pageNo);
        page.setTotalRecords(count);
        long pageCount = count / pageSize;
        if (count > pageSize * pageCount) {
            pageCount++;
        }
        page.setPagesAvailable((int)pageCount);
        return page;

    }
    
    
    @Override
    public VendorMappingStateProxy getCurrentStateOfSupplierMapping(String companyCode) throws Exception {
        return new RestTemplate().getForObject(restServiceUrl.getSupplierStatusURI(), VendorMappingStateProxy.class, companyCode);
    }


    @Override
    public void downloadSupplierMapping(String unitid,String filename, List<String> supplierIds) throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("supplierIds", supplierIds);
    	params.put("unitId", Integer.parseInt(unitid));
    	params.put("fileName", filename);
    	
    	if(supplierIds!= null){
    		new RestTemplate().postForObject(restServiceUrl.getDownloadSupplierMappingURI(),params, Integer.class);
    	}  	
    }
    
    private class MaterialGroupCollection extends ArrayList<MaterialGroupProxy>{}
}
