package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service.comparator;

import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.ComparisonCriteria;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.WeatherCondition;
import java.util.Comparator;

public interface WeatherConditionComparator extends Comparator<WeatherCondition> {

  boolean apply(ComparisonCriteria comparisonCriteria);

}
