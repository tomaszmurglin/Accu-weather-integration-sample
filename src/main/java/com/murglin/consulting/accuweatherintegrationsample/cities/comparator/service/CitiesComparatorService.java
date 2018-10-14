package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service;

import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.command.AccuWeatherHttpCommand;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.Weather;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CitiesComparatorService {

  private final AccuWeatherHttpCommand accuWeatherHttpCommand;

  public String chooseCityWithTheBestWeather(Set<String> citiesNames) {
    Collection<Weather> weathers = accuWeatherHttpCommand.fetchLocationKeysForGivenCitiesNames(citiesNames);
    //TODO compare by given criteria
    return "";
  }
}
