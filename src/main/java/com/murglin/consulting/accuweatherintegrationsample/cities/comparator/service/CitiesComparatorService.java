package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service;

import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client.AccuWeatherCurrentConditionsClient;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client.AccuWeatherLocationsClient;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception.WeatherConditionComparatorNotFoundException;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.ComparisonCriteria;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.WeatherCondition;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.dto.WeatherConditionDTO;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service.comparator.WeatherConditionComparator;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CitiesComparatorService {

  private final List<WeatherConditionComparator> weatherConditionComparators;

  private final AccuWeatherLocationsClient accuWeatherLocationsClient;

  private final AccuWeatherCurrentConditionsClient accuWeatherCurrentConditionsClient;

  public List<WeatherConditionDTO> compareCitiesByWeatherConditions(Set<String> citiesNames,
      ComparisonCriteria comparisonCriteria)
      throws IOException {
    Map<String, String> citiesNamesToLocationKeys = accuWeatherLocationsClient
        .fetchLocationKeysForGivenCitiesNames(citiesNames);

    Map<String, WeatherCondition> citiesNamesToWeatherConditions = accuWeatherCurrentConditionsClient
        .fetchCurrentWeatherConditionsForGivenCitiesKeys(citiesNamesToLocationKeys);

    WeatherConditionComparator comparator = weatherConditionComparators.stream()
        .filter(c -> c.apply(comparisonCriteria)).findFirst()
        .orElseThrow(WeatherConditionComparatorNotFoundException::new);

    return citiesNamesToWeatherConditions.entrySet()
        .stream()
        .sorted((e1, e2) -> comparator.reversed().compare(e1.getValue(), e2.getValue()))
        .map(e -> new WeatherConditionDTO(e.getKey(), e.getValue())).collect(
            Collectors.toList());

  }
}
