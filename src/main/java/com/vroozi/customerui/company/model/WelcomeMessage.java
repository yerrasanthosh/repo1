package com.vroozi.customerui.company.model;

import java.util.List;

/**
 * User: Muhammad Salman Nafees
 * Date: 10/19/12
 * Time: 4:44 PM
 */
public class WelcomeMessage {

    private int id;
    private int unitId;
    private String message;
    private String welcomeImage;
    private List<String> mailingList;
    private boolean welcomeWidgetText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMailingList() {
        return mailingList;
    }

    public void setMailingList(List<String> mailingList) {
        this.mailingList = mailingList;
    }

    public String getWelcomeImage() {
	return welcomeImage;
    }

    public void setWelcomeImage(String welcomeImage) {
	this.welcomeImage = welcomeImage;
    }

    public boolean isWelcomeWidgetText() {
      return welcomeWidgetText;
    }

    public void setWelcomeWidgetText(boolean welcomeWidgetText) {
      this.welcomeWidgetText = welcomeWidgetText;
    }
}
