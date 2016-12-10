package com.vroozi.customerui.locale.services.rest;

import java.util.List;

import com.vroozi.customerui.supplier.model.TimeZone;

/**
 * @author Muhammad Haris
 *
 */
public interface LocaleRestClient {

  public List<TimeZone> getAllTimeZones() throws Exception;
}
