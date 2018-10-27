package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.dto;

import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.WeatherCondition;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import lombok.Value;

@Value
@Immutable
@ThreadSafe
public final class WeatherConditionDTO {

  private final String cityName;
  private final WeatherCondition weatherCondition;
}
