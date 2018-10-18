package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception.AccuWeatherServiceNotAvailableException;
import com.murglin.consulting.accuweatherintegrationsample.configuration.AccuWeatherConfig;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AccuWeatherLocationsClient {

  private final AccuWeatherConfig accuWeatherConfig;

  @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand(fallbackMethod = "getFallback", threadPoolProperties = {
      @HystrixProperty(name = "maxQueueSize", value = "-1"),
      @HystrixProperty(name = "coreSize", value = "10")}, commandProperties = {
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "5000")})
  private String fetchLocationKeyForGivenCitiesName(String cityName,
      AccuWeatherQuery.AccuWeatherQueryBuilder accuWeatherQueryBuilder)
      throws IOException {
    AccuWeatherQuery accuWeatherQuery = accuWeatherQueryBuilder.query(cityName).build();
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate
        .getForEntity(accuWeatherQuery.getUrl(), String.class);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(response.getBody());
    return root.get(0).path("Key").asText();
  }

  public Set<String> fetchLocationKeysForGivenCitiesNames(Set<String> citiesNames)
      throws IOException {
    var accuWeatherApi = accuWeatherConfig.getApi();

    AccuWeatherQuery.AccuWeatherQueryBuilder accuWeatherQueryBuilder = new AccuWeatherQuery.AccuWeatherQueryBuilder(
        accuWeatherApi.getHostName());
    accuWeatherQueryBuilder = accuWeatherQueryBuilder.locations()
        .version(accuWeatherApi.getVersion()).cities()
        .search().apiKey(accuWeatherApi.getApiKey());

    List<String> citiesKeys = Lists.newArrayList();

    for (String cityName : citiesNames) {
      String key = fetchLocationKeyForGivenCitiesName(cityName, accuWeatherQueryBuilder);
      citiesKeys.add(key);
    }

    return ImmutableSet.copyOf(citiesKeys);
  }

  private Set<String> getFallback(Set<String> citiesNames, Throwable reason) {
    // simply failfast without trying to keep promise
    throw new AccuWeatherServiceNotAvailableException();
  }
}
