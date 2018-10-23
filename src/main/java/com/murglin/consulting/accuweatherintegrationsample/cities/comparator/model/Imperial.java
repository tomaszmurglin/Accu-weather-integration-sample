
package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// No immutable cause of https://github.com/rzwitserloot/lombok/issues/1563
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Imperial {

  @JsonProperty("Value")
  private Integer value;
  @JsonProperty("Unit")
  private String unit;
  @JsonProperty("UnitType")
  private Integer unitType;

}
