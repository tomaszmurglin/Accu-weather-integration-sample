package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.controller;

import static com.murglin.consulting.accuweatherintegrationsample.cities.comparator.controller.CitiesComparatorController.MAPPING;

import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception.InvalidComparisonCriteriaException;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception.SameCitiesRequestedException;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.ComparisonCriteria;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.WeatherCondition;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service.CitiesComparatorService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MAPPING)
@RequiredArgsConstructor
public class CitiesComparatorController {
    
    /*
      TODO:
      logging, exception handling, security, cloud, validation, persistance, presentation layer, javadocs with author, springfox swagger, api
      versioning, dockerize it with Fabric8 plugin or google JIB, sevrlet 3.0 api async, apsects for logging, readme, move secrets to spirng cloud config vault, tests
      replace feign instead of rest templates, protect db integration with Hystrix also, implements Hystrix collapse method, use https to call accu weather,
      tomcat gracefull shutdown
     */

  static final String MAPPING = "cities/compare";

  private final CitiesComparatorService citiesComparatorService;

  @GetMapping(value = "/{citiesNames}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<WeatherCondition> compareCitiesByWeatherConditions(@PathVariable String[] citiesNames,
      @RequestParam("criteria") String comparisonCriteria)
      throws IOException {

    boolean isComparisonCriteriaNotValid = Arrays.stream(ComparisonCriteria.values())
        .map(Enum::toString)
        .noneMatch(e -> e.equalsIgnoreCase(comparisonCriteria));
    if (isComparisonCriteriaNotValid) {
      throw new InvalidComparisonCriteriaException();
    }

    Set<String> citiesNamesSet = Arrays.stream(citiesNames).collect(
        Collectors.toSet());
    if (citiesNamesSet.size() < 2) {
      throw new SameCitiesRequestedException();
    }

    return citiesComparatorService
        .compareCitiesByWeatherConditions(citiesNamesSet);
  }
}

