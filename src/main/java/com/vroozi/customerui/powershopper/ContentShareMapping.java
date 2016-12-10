package com.vroozi.customerui.powershopper;

import java.util.List;

public class ContentShareMapping {

  private String id;
  private String firstName;
  private String lastName;
  private String username;
  private List<String> sharedContentGroups;
  private List<String> sharedContentGroupTokens;
  private String email;
  private String unitId;
  private boolean deleted;


  public List<String> getSharedContentGroupTokens() {
    return sharedContentGroupTokens;
  }

  public void setSharedContentGroupTokens(List<String> sharedContentGroupTokens) {
    this.sharedContentGroupTokens = sharedContentGroupTokens;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getSharedContentGroups() {
    return sharedContentGroups;
  }

  public void setSharedContentGroups(List<String> sharedContentGroups) {
    this.sharedContentGroups = sharedContentGroups;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
}
