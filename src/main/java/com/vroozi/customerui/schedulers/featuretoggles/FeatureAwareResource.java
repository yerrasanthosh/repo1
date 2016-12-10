package com.vroozi.customerui.schedulers.featuretoggles;

import com.vroozi.customerui.util.PrettyPrintingMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qureshit on 2/13/2016.
 */
@Component
public class FeatureAwareResource {
  private static final Logger LOG = LoggerFactory.getLogger(FeatureAwareResource.class);

  private List<FeatureToggle> featureToggleList = new ArrayList<>();

  private Map<String, FeatureToggle> featureToggleMap = new HashMap<>();

  public void setFeatureToggleList(List<FeatureToggle> featureToggleList) {
    this.featureToggleList = featureToggleList;
  }

  void updateFeatureToggleMap() {
    try {
      if (featureToggleList == null) {
        return;
      }
      for (FeatureToggle featureToggle : featureToggleList) {
        if (featureToggle == null) {
          continue;
        }
        featureToggleMap.put(featureToggle.getFeatureName().toString().trim(), featureToggle);
      }
    } catch (Exception e) {
      LOG.error("An exception occurred updating feature toggle map!: ", e);
    }

  }

  public boolean isFeatureAvailable(String unitId, String featureName) {

    try {
      //do we even have a feature with this name?
      FeatureToggle featureToggle = featureToggleMap.get(featureName);

      //if its not enabled by vroozi return false
      if (!featureToggle.isEnabled()) {
        return false;
      }

      //is it available to everyone? If so, return true
      if ((featureToggle.getSubscriberUnitIds()==null || featureToggle.getSubscriberUnitIds().isEmpty())
          && (featureToggle.getExcludedUnitIds()==null || featureToggle.getExcludedUnitIds().isEmpty())) {
        return true;
      }

      //if feature is excluded for few companies, is this company also excluded?
      if (featureToggle.getExcludedUnitIds().contains(unitId)){
        return false;
      }

      //if feature is made exclusively available to select few companies, is this company included?
      if (featureToggle.getSubscriberUnitIds().contains(unitId)){
        return true;
      }
    } catch (Exception e) {
      LOG.error("Something went wrong when trying to evaluate the feature availability"
                + " for " + featureName +": ", e);
      return false;
    }
    return false;
  }

  @Override
  public String toString() {
    return new PrettyPrintingMap<>(featureToggleMap).toString();
  }


}
