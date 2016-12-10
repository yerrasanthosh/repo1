package com.vroozi.customerui.locale.services.rest;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.supplier.model.TimeZone;
import com.vroozi.customerui.util.RestServiceUrl;

/**
 * @author Muhammad Haris
 *
 */
@Component
public class LocaleRestClientImpl implements LocaleRestClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocaleRestClientImpl.class);

  @Autowired
  private RestServiceUrl restServiceUrl;

  @Override
  public List<TimeZone> getAllTimeZones() throws Exception {
      List<TimeZone> list = null;
      try {
          TimeZone[] listOfTimeZones = new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/timeformat/", TimeZone[].class);
          list = Arrays.asList(listOfTimeZones);
      } catch(RestClientException rse) {
          LOGGER.error("Error retrieving time zones!", rse);
          throw rse;
      } catch(Exception exp) {
          LOGGER.error("Error retrieving time zones!", exp);
          throw new AdminUIException(exp);
      }

      return list;
  }
}
