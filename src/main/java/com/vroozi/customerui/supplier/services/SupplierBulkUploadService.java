package com.vroozi.customerui.supplier.services;

import com.vroozi.customerui.catalog.model.MaterialGroupPost;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.model.SupplierBulkUploadStatus;
import com.vroozi.customerui.util.Page;

public interface SupplierBulkUploadService {

    void uploadSupplierBulk(MaterialGroupPost materialGroupPost) throws Exception;

    Page<Supplier> getPagedDataForCustomSupplierBulkUpload(int unitId, int pageNo, int pageSize, String searchTerm, String uniqueSupplierId) throws Exception;

    SupplierBulkUploadStatus getCurrentStateOfSupplierBulkUpload(String companyCode) throws Exception;

    void downloadSupplierBulkUpload(String unitid, String path, boolean fetchSuppliers)throws Exception;
    
}
