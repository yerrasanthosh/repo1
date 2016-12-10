package com.vroozi.customerui.schedulers.featuretoggles;

import com.vroozi.customerui.user.services.rest.UserRestClient;
import com.vroozi.customerui.user.services.user.model.Result;
import com.vroozi.customerui.util.RestServiceUrl;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by qureshit on 2/13/2016.
 */
@Component
public class FeatureToggleTimerTask extends TimerTask {

  private static final Logger LOG = LoggerFactory.getLogger(FeatureAwareResource.class);

  @Autowired
  private RestServiceUrl restServiceUrl;

  @Autowired
  private FeatureAwareResource featureAwareResource;

  @Override
  public void run() {
    try {
      featureAwareResource.setFeatureToggleList(getFeatureToggles(null, null, null));
      featureAwareResource.updateFeatureToggleMap();
      LOG.info("Got feature map: " + featureAwareResource.toString());
    } catch (Exception e ) {
      LOG.error("Unable to update feature map!: ", e);
    }
  }

  private List<FeatureToggle> getFeatureToggles(String unitId, String featureId,
                                               String featureName) {

    FeatureToggle[] featureToggles;
    Result<FeatureToggle> featureToggleResult = new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/feature-toggles", Result.class, unitId, featureId,featureName);
    ObjectMapper mapper = new ObjectMapper();
    featureToggles =
        mapper.convertValue(featureToggleResult.getDataItems(),
                            FeatureToggle[].class);

    return  Arrays.asList(featureToggles);
  }

}
