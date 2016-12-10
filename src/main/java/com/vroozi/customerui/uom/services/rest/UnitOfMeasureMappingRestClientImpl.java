/**
 *
 */
package com.vroozi.customerui.uom.services.rest;

import com.vroozi.customerui.uom.controller.UnitOfMeasureMappingService;
import com.vroozi.customerui.uom.model.UnitOfMeasureMapping;
import com.vroozi.customerui.uom.model.UnitOfMeasureMappingPost;
import com.vroozi.customerui.uom.model.UnitOfMeasureMappingStatus;
import com.vroozi.customerui.util.Page;
import com.vroozi.customerui.util.RestServiceUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Tahir on 6/13/14.
 */

@Component
public class UnitOfMeasureMappingRestClientImpl implements UnitOfMeasureMappingService {

    private final Logger LOGGER = LoggerFactory.getLogger(UnitOfMeasureMappingRestClientImpl.class);

    @Autowired
    RestServiceUrl restServiceUrl;


    @Override
    public void uploadUOMMapping(UnitOfMeasureMappingPost unitOfMeasureMappingPost) {
        new RestTemplate().postForLocation(restServiceUrl.uomMappingUploadURI(), unitOfMeasureMappingPost);
    }

    @Override
    public UnitOfMeasureMappingStatus getUOMMappingStatus(String unitId) {
        try {
            UnitOfMeasureMappingStatus mappingStatus = new RestTemplate().getForObject(restServiceUrl.uomMappingStatusURI(), UnitOfMeasureMappingStatus.class, unitId);
            return mappingStatus;
        } catch (Exception e) {
            LOGGER.error("ERROR WHILE CHECKING PROFILE GROUP STATUS...", e);
        }
        return null;
    }


    @Override
    public Page<UnitOfMeasureMapping> getPagedUOMMapping(String unitId, int pageNumber, int pageSize, String searchTerm) {
        Long count = new RestTemplate().getForObject(restServiceUrl.uomMappingCountURI(), Long.class, unitId, searchTerm);
        List<UnitOfMeasureMapping> list = (List<UnitOfMeasureMapping>) new RestTemplate().getForObject(restServiceUrl.uomMappingPageURI(), List.class, unitId, pageNumber, pageSize, searchTerm);
        Page<UnitOfMeasureMapping> page = new Page<UnitOfMeasureMapping>();
        page.setPageItems(list);
        page.setPageNumber(pageNumber);
        page.setTotalRecords(count);
        long pageCount = count / pageSize;
        if (count > pageSize * pageCount) {
            pageCount++;
        }
        page.setPagesAvailable((int) pageCount);
        return page;
    }

    @Override
    public void downloadMappings(String unitid, String path) throws Exception {
        new RestTemplate().getForObject(restServiceUrl.downloadUOMMappingURI() + "/unitid/" + unitid + "?filename=" + path, Integer.class);
    }
}
