package com.vroozi.customerui.appConfig.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vroozi.customerui.locale.services.rest.LocaleRestClient;
import com.vroozi.customerui.supplier.model.TimeZone;

/**
 * @author Muhammad Haris
 *
 */
@Service
public class AdminUICacheServiceImpl implements AdminUICacheService {

  private static final Logger LOG = LoggerFactory.getLogger(AdminUICacheServiceImpl.class);

  @Autowired
  private LocaleRestClient localeRestClient;

  @Override
  public List<TimeZone> getAllTimeZones() {

    LOG.info("------- Caching Time Zones -------");
    List<TimeZone> timeZones = new ArrayList<>();
    try {
      timeZones = localeRestClient.getAllTimeZones();
      LOG.info("Time Zones Count: {}", timeZones.size());
    } catch (Exception e) {
      LOG.error("Exception in Caching TimeZones", e);
      e.printStackTrace();
    }

    return timeZones;
  }

}
