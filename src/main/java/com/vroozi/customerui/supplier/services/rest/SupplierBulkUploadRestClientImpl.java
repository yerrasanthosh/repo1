package com.vroozi.customerui.supplier.services.rest;


import com.vroozi.customerui.catalog.model.MaterialGroupPost;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.model.SupplierBulkUploadStatus;
import com.vroozi.customerui.util.Page;
import com.vroozi.customerui.util.RestServiceUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class SupplierBulkUploadRestClientImpl implements SupplierBulkUploadRestClient{
	
    @Autowired
    RestServiceUrl restServiceUrl;

    @Override
    public void uploadSupplierBulk(MaterialGroupPost materialGroupPost) throws Exception {
        try{
            new RestTemplate().postForLocation(restServiceUrl.getUploadSupplierBulkURI(), materialGroupPost);
        }catch (Exception exp) {
            throw new AdminUIException(exp);
        }
    }

    @Override
    public Page<Supplier> getPagedDataForCustomSupplierBulkUpload(int unitId, int pageSize, int pageNo, String searchTerm, String uniqueSupplierId) throws Exception {
        Long count = new RestTemplate().getForObject(restServiceUrl.getCountSupplierBulkUploadURI(), Long.class, unitId, searchTerm, uniqueSupplierId);
        List<Supplier> list=(List<Supplier>)new RestTemplate().getForObject(restServiceUrl.getSupplierBulkUploadPaginationURI(), List.class, unitId, pageNo, pageSize, searchTerm, uniqueSupplierId);

        Page<Supplier> page = new Page<Supplier>();
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
    public SupplierBulkUploadStatus getCurrentStateOfSupplierBulkUpload(String companyCode) throws Exception {
        return new RestTemplate().getForObject(restServiceUrl.getSupplierBulkUploadStatusURI(), SupplierBulkUploadStatus.class, companyCode);
    }


    @Override
    public void downloadSupplierBulkUpload(String unitid, String filename, boolean fetchSuppliers) throws Exception {
    	new RestTemplate().getForObject(restServiceUrl.getDownloadSupplierBulkUploadURI(), Integer.class,unitid,filename, fetchSuppliers);

    }

}
