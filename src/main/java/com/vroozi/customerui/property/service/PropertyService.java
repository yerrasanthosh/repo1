package com.vroozi.customerui.property.service;

import com.vroozi.customerui.property.model.PropertyProxy;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 9/12/12
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PropertyService {
    List<PropertyProxy> getPropertiesByCatalogId(String catalogId) throws Exception;

    PropertyProxy createProperty(PropertyProxy property) throws Exception;
}
