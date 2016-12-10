/**
 *
 */
package com.vroozi.customerui.appConfig.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vroozi.customerui.appConfig.service.AdminUICacheService;
import com.vroozi.customerui.supplier.model.TimeZone;
import com.vroozi.customerui.util.SpringApplicationContext;

/**
 * Represents Cached Data
 *
 * @author Muhammad Haris
 */
public class AdminUICache {

  private static Logger LOGGER = LoggerFactory.getLogger(AdminUICache.class);

  private static List<TimeZone> timeZonesList;
  private static Map<Integer, TimeZone> timeZonesMap = new HashMap<>();
  private static Map<String, TimeZone> timeZonesByOffsetMap = new HashMap<>();

  public static List<TimeZone> getTimeZonesList() {
    initializeTimeZones();
    return timeZonesList;
  }

  private static synchronized void initializeTimeZones() {
    if (timeZonesList == null) {
      AdminUICacheService adminUICacheService =
          (AdminUICacheService) SpringApplicationContext.getBean("adminUICacheServiceImpl");
      timeZonesList = adminUICacheService.getAllTimeZones();
    }
  }

  public static Map<Integer, TimeZone> getTimeZonesMap() {
    synchronized (timeZonesMap) {
      if (timeZonesMap.size() == 0) {
        initializeTimeZones();

        for (TimeZone timeZone : timeZonesList) {
          LOGGER.debug("Mapping Time Zone: {}", timeZone.getTimeZoneId());
          timeZonesMap.put(timeZone.getTimeZoneId(), timeZone);
        }
      }
    }

    return timeZonesMap;
  }

  public static Map<String, TimeZone> getTimeZonesByOffsetMap() {
    synchronized (timeZonesByOffsetMap) {
      if (timeZonesByOffsetMap.size() == 0) {
        initializeTimeZones();

        for (TimeZone timeZone : timeZonesList) {
          LOGGER.debug("Mapping Time Zone By Offset: {}", timeZone.getTimeZoneValue());
          timeZonesByOffsetMap.put(timeZone.getTimeZoneValue(), timeZone);
        }
      }
    }

    return timeZonesByOffsetMap;
  }
}
