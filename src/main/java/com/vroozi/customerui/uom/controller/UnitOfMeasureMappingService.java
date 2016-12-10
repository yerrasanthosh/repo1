package com.vroozi.customerui.uom.controller;

import com.vroozi.customerui.uom.model.UnitOfMeasureMapping;
import com.vroozi.customerui.uom.model.UnitOfMeasureMappingPost;
import com.vroozi.customerui.uom.model.UnitOfMeasureMappingStatus;
import com.vroozi.customerui.util.Page;

/**
 * Created by Tahir on 6/6/14.
 */
public interface UnitOfMeasureMappingService {
    void uploadUOMMapping(UnitOfMeasureMappingPost unitOfMeasureMappingPost);

    UnitOfMeasureMappingStatus getUOMMappingStatus(String unitId);

    Page<UnitOfMeasureMapping> getPagedUOMMapping(String unitId, int pageNumber, int pageSize, String searchTerm);

    void downloadMappings(String unitId, String fileName) throws Exception;
}
