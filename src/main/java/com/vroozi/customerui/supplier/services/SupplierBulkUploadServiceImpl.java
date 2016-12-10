package com.vroozi.customerui.supplier.services;

import com.vroozi.customerui.catalog.model.MaterialGroupPost;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.model.SupplierBulkUploadStatus;
import com.vroozi.customerui.supplier.services.rest.SupplierBulkUploadRestClient;
import com.vroozi.customerui.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierBulkUploadServiceImpl implements SupplierBulkUploadService{
    @Autowired
    private SupplierBulkUploadRestClient supplierBulkUploadRestClient;

    @Override
    public void uploadSupplierBulk(MaterialGroupPost materialGroupPost) throws Exception {
        supplierBulkUploadRestClient.uploadSupplierBulk(materialGroupPost);
    }

    @Override
    public Page<Supplier> getPagedDataForCustomSupplierBulkUpload(int unitId, int pageNo, int pageSize, String searchTerm, String uniqueSupplierId) throws Exception {
        return supplierBulkUploadRestClient.getPagedDataForCustomSupplierBulkUpload(unitId, pageSize, pageNo, searchTerm, uniqueSupplierId);
    }

    @Override
    public SupplierBulkUploadStatus getCurrentStateOfSupplierBulkUpload(String companyCode) throws Exception {
        return supplierBulkUploadRestClient.getCurrentStateOfSupplierBulkUpload(companyCode);
    }

    @Override
    public void downloadSupplierBulkUpload(String unitid, String path, boolean fetchSuppliers) throws Exception{
        supplierBulkUploadRestClient.downloadSupplierBulkUpload(unitid, path, fetchSuppliers);
    }
    
}
