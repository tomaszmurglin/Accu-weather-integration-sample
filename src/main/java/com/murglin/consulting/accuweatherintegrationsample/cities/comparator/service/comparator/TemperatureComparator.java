package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service.comparator;

import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.ComparisonCriteria;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.WeatherCondition;
import java.math.BigDecimal;

public class TemperatureComparator implements WeatherConditionComparator {

  @Override
  public int compare(WeatherCondition o1, WeatherCondition o2) {
    BigDecimal weatherCondition1 = BigDecimal.valueOf(o1.getTemperature().getMetric().getValue());
    BigDecimal weatherCondition2 = BigDecimal.valueOf(o2.getTemperature().getMetric().getValue());
    return weatherCondition1.compareTo(weatherCondition2);
  }

  @Override
  public boolean apply(ComparisonCriteria comparisonCriteria) {
    return comparisonCriteria == ComparisonCriteria.TEMPERATURE;
  }
}
