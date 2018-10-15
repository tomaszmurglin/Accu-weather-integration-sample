package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.controller;

import static com.murglin.consulting.accuweatherintegrationsample.cities.comparator.controller.CitiesComparatorController.MAPPING;

import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.ComparisonCriteria;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service.CitiesComparatorService;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MAPPING)
@RequiredArgsConstructor
public class CitiesComparatorController {
    
    /*
      TODO:
      logging, exception handling, security, cloud, validation, persistance, presentation layer, javadocs with author, springfox, api
      versioning, dockerize it, sevrlet 3.0 api async, apsects for logging, readme, move secrets to spirng cloud config vault, tests
      replace feign instead of rest templates
     */

  static final String MAPPING = "cities/compare";

  private final CitiesComparatorService citiesComparatorService;

  @GetMapping(value = "/{citiesNames}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String chooseCityWithTheBestWeather(@PathVariable String[] citiesNames,
      @RequestParam("criteria") String comparisonCriteria)
      throws IOException {
    boolean isComparisonCriteriaNotValid = Arrays.stream(ComparisonCriteria.values())
        .map(Enum::toString)
        .noneMatch(e -> e.equalsIgnoreCase(comparisonCriteria));
    if (isComparisonCriteriaNotValid) {
      //TODO write RestControllerAdvice
      return ResponseB
    }
    return citiesComparatorService
        .chooseCityWithTheBestWeather(Arrays.stream(citiesNames).collect(
            Collectors.toSet()));
  }
}

