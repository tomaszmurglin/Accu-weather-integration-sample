package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service.comparator;

import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.ComparisonCriteria;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.WeatherCondition;

public class TemperatureComparator implements WeatherConditionComparator {

  @Override
  public int compare(WeatherCondition o1, WeatherCondition o2) {
    return 0;
  }

  @Override
  public boolean apply(ComparisonCriteria comparisonCriteria) {
    return comparisonCriteria == ComparisonCriteria.TEMPERATURE;
  }
}
