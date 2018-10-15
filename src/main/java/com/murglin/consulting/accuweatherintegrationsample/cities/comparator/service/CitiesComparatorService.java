package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service;

import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client.AccuWeatherCurrentConditionsClient;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client.AccuWeatherLocationsClient;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.WeatherCondition;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CitiesComparatorService {

  private final AccuWeatherLocationsClient accuWeatherLocationsClient;

  private final AccuWeatherCurrentConditionsClient accuWeatherCurrentConditionsClient;

  public String chooseCityWithTheBestWeather(Set<String> citiesNames) throws IOException {
    Collection<String> citiesLocationKeys = accuWeatherLocationsClient
        .fetchLocationKeysForGivenCitiesNames(citiesNames);
    Collection<WeatherCondition> weatherConditions = accuWeatherCurrentConditionsClient
        .fetchCurrentWeatherConditionsForGivenCitiesKeys(citiesLocationKeys);
    //TODO compare by given criteria
    return "";
  }
}
