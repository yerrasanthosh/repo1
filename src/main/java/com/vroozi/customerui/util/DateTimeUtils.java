package com.vroozi.customerui.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date time utility methods
 *
 * @author Muhammad Haris
 *
 */
public class DateTimeUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtils.class);
  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

  /**
   * Gets current date time as GMT Date
   *
   * @return
   * @throws Exception
   * @author Muhammad Haris
   */
  public static Date getCurrentDateForGMT() {
    String currentDate = formatDateForTimeZone(new Date(), DATE_TIME_FORMAT, "");
    try {
      return parseDate(currentDate, DATE_TIME_FORMAT);
    } catch (Exception e) {
      LOGGER.error("Exception in Getting Current Date For GMT", e);
      return null;
    }
  }

  /**
   * Formats given date according to the given format and time zone
   *
   * @param date date to format
   * @param dateFormat format according to which it is to be formatted
   * @param timeZoneValue {@link String} containing time zone value e.g., +5:00
   * @return
   * @author Muhammad Haris
   */
  public static String formatDateForTimeZone(Date date, String dateFormat, String timeZoneValue) {
    DateFormat formatter = getDateFormatter(dateFormat != null ? dateFormat : DATE_TIME_FORMAT);
    formatter.setTimeZone(TimeZone.getTimeZone("GMT" + timeZoneValue));
    return formatter.format(date);
  }

  /**
   * Formats given date according to the given format
   *
   * @param date
   * @param dateFormat
   * @return
   */
  public static String formatDate(Date date, String dateFormat) {
    DateFormat formatter = getDateFormatter(dateFormat != null ? dateFormat : DATE_TIME_FORMAT);
    return formatter.format(date);
  }

  /**
   * Parses given string as GMT {@link Date}
   *
   * @param date {@link String} containing date
   * @param dateFormat format in which date is to parsed as
   * @return
   * @author Muhammad Haris
   */
  public static Date parseDateForGMT(String date, String dateFormat) throws Exception {
    DateFormat formatter = getDateFormatter(dateFormat != null ? dateFormat : DATE_TIME_FORMAT);
    formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    return formatter.parse(date);
  }

  public static Date parseDateForTimeZone(String date, String dateFormat, String timeZoneValue)
      throws Exception {
    DateFormat formatter = getDateFormatter(dateFormat != null ? dateFormat : DATE_TIME_FORMAT);
    formatter.setTimeZone(TimeZone.getTimeZone("GMT" + timeZoneValue));
    return formatter.parse(date);
  }

  /**
   * Parses given string as {@link Date}
   *
   * @param date {@link String} containing date
   * @param dateFormat format in which date is to parsed as
   * @return
   * @author Muhammad Haris
   */
  public static Date parseDate(String date, String dateFormat) throws Exception {
    DateFormat formatter = getDateFormatter(dateFormat != null ? dateFormat : DATE_TIME_FORMAT);
    return formatter.parse(date);
  }

  /**
   * Gets {@link DateFormat} object against given dateFormat
   *
   * @param dateFormat {@link String} containing date format
   * @return
   * @author Muhammad Haris
   */
  public static DateFormat getDateFormatter(String dateFormat) {
    DateFormat formatter = new SimpleDateFormat(dateFormat);
    formatter.setLenient(false);
    return formatter;
  }
}
