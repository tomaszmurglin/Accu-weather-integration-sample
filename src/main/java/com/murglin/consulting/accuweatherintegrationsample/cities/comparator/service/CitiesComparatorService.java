package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service;

import com.google.common.collect.Ordering;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client.AccuWeatherCurrentConditionsClient;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client.AccuWeatherLocationsClient;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception.WeatherConditionComparatorNotFoundException;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.ComparisonCriteria;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.WeatherCondition;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service.comparator.WeatherConditionComparator;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CitiesComparatorService {

  private final List<WeatherConditionComparator> weatherConditionComparators;

  private final AccuWeatherLocationsClient accuWeatherLocationsClient;

  private final AccuWeatherCurrentConditionsClient accuWeatherCurrentConditionsClient;

  public List<WeatherCondition> compareCitiesByWeatherConditions(Set<String> citiesNames,
      ComparisonCriteria comparisonCriteria)
      throws IOException {
    Set<String> citiesLocationKeys = accuWeatherLocationsClient
        .fetchLocationKeysForGivenCitiesNames(citiesNames);
    List<WeatherCondition> weatherConditions = accuWeatherCurrentConditionsClient
        .fetchCurrentWeatherConditionsForGivenCitiesKeys(citiesLocationKeys);
    WeatherConditionComparator comparator = weatherConditionComparators.stream()
        .filter(c -> c.apply(comparisonCriteria)).findFirst()
        .orElseThrow(WeatherConditionComparatorNotFoundException::new);
    return Ordering.natural().immutableSortedCopy(weatherConditions);
  }
}
