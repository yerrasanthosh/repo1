package com.vroozi.customerui.schedulers.featuretoggles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qureshit on 2/13/2016.
 */
public class FeatureToggle {
  private String featureId;
  private Feature featureName;
  private List<String> subscriberUnitIds = new ArrayList<>();
  private List<String> excludedUnitIds = new ArrayList<>();
  private boolean enabled = false;


  public String getFeatureId() {
    return featureId;
  }

  public void setFeatureId(String featureId) {
    this.featureId = featureId;
  }

  public Feature getFeatureName() {
    return featureName;
  }

  public void setFeatureName(Feature featureName) {
    this.featureName = featureName;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public String toString() {
    return "Feature Name: " + featureName + ", enabled: " + enabled +
           ", subscriber unitIds: " + ((subscriberUnitIds==null)?"":subscriberUnitIds.toString()) +
           ", excluded unitIds: " + ((excludedUnitIds==null)?"":excludedUnitIds.toString());
  }

  public List<String> getSubscriberUnitIds() {
    return subscriberUnitIds;
  }

  public void setSubscriberUnitIds(List<String> subscriberUnitIds) {
    this.subscriberUnitIds = subscriberUnitIds;
  }

  public List<String> getExcludedUnitIds() {
    return excludedUnitIds;
  }

  public void setExcludedUnitIds(List<String> excludedUnitIds) {
    this.excludedUnitIds = excludedUnitIds;
  }
}
