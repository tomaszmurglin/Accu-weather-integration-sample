
package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// No immutable cause of https://github.com/rzwitserloot/lombok/issues/1563
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WeatherCondition {

  @JsonProperty("LocalObservationDateTime")
  private String localObservationDateTime;
  @JsonProperty("EpochTime")
  private Integer epochTime;
  @JsonProperty("WeatherText")
  private String weatherText;
  @JsonProperty("WeatherIcon")
  private Integer weatherIcon;
  @JsonProperty("IsDayTime")
  private Boolean isDayTime;
  @JsonProperty("Temperature")
  private Temperature temperature;
  @JsonProperty("MobileLink")
  @JsonIgnore
  private String mobileLink;
  @JsonProperty("Link")
  @JsonIgnore
  private String link;
}
