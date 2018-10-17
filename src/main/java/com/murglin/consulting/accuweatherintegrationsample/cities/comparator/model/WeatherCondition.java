
package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import lombok.Value;

@ThreadSafe
@Immutable
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WeatherCondition {

  private final String localObservationDateTime;
  private final Integer epochTime;
  private final String weatherText;
  private final Integer weatherIcon;
  private final Boolean isDayTime;
  private final Temperature temperature;
  private final String mobileLink;
  private final String link;
}
