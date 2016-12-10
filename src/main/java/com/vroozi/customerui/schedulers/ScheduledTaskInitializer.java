package com.vroozi.customerui.schedulers;

import com.vroozi.customerui.schedulers.featuretoggles.FeatureToggleTimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by qureshit on 2/13/2016.
 */
@Component
public class ScheduledTaskInitializer {

  private static final Logger LOG = LoggerFactory.getLogger(ScheduledTaskInitializer.class);
  private ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

  @Autowired
  private FeatureToggleTimerTask featureToggleTimerTask;

  @Value("${feature.toggle.timer.task.period}")
  private long period;

  @Value("${feature.toggle.timer.task.delay}")
  private long delay;

  @PostConstruct
  public void initMethod() {
    exec.scheduleAtFixedRate(featureToggleTimerTask, delay, period, TimeUnit.MINUTES);
    LOG.info("Scheduling a new timer task");
  }

  @PreDestroy
  public void destroyMethod() {
    exec.shutdown();
  }
}

