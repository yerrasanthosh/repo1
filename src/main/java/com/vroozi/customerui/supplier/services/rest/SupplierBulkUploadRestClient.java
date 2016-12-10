package com.vroozi.customerui.supplier.services.rest;

import com.vroozi.customerui.catalog.model.MaterialGroupPost;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.model.SupplierBulkUploadStatus;
import com.vroozi.customerui.util.Page;

public interface SupplierBulkUploadRestClient {

    void uploadSupplierBulk(MaterialGroupPost materialGroupPost) throws Exception;

    Page<Supplier> getPagedDataForCustomSupplierBulkUpload(int unitId, int pageSize, int pageNo, String searchTerm, String uniqueSupplierId) throws Exception;

    SupplierBulkUploadStatus getCurrentStateOfSupplierBulkUpload(String companyCode) throws Exception;
    void downloadSupplierBulkUpload(String unitid, String filename, boolean fetchSuppliers)throws Exception;
}
