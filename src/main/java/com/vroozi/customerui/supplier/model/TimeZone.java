package com.vroozi.customerui.supplier.model;

/**
 * Created by IntelliJ IDEA.
 * User: rashidha
 * Date: 9/18/12
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimeZone {
    private int timeZoneId;
    private String timeZoneName;
    private String timeZoneValue;

    public int getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(int timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getTimeZoneName() {
        return timeZoneName;
    }

    public void setTimeZoneName(String timeZoneName) {
        this.timeZoneName = timeZoneName;
    }

    public String getTimeZoneValue() {
        return timeZoneValue;
    }

    public void setTimeZoneValue(String timeZoneValue) {
        this.timeZoneValue = timeZoneValue;
    }
}
