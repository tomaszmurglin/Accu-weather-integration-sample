package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception.AccuWeatherException;
import com.murglin.consulting.accuweatherintegrationsample.configuration.AccuWeatherConfig;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccuWeatherLocationsClient {

  private final AccuWeatherConfig accuWeatherConfig;

  @Autowired
  public AccuWeatherLocationsClient(AccuWeatherConfig accuWeatherConfig) {
    this.accuWeatherConfig = accuWeatherConfig;
  }

  @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand(fallbackMethod = "getFallback", threadPoolProperties = {
      @HystrixProperty(name = "maxQueueSize", value = "-1"),
      @HystrixProperty(name = "corePoolSize", value = "10")}, commandProperties = {
      @HystrixProperty(name = "circuitBreakerRequestVolumeThreshold", value = "5"),
      @HystrixProperty(name = "metricsRollingStatisticalWindowInMilliseconds", value = "5000")})
  public List<String> fetchLocationKeysForGivenCitiesNames(Set<String> citiesNames)
      throws IOException {
    var accuWeatherApi = accuWeatherConfig.getApi();

    AccuWeatherQuery.AccuWeatherQueryBuilder accuWeatherQueryBuilder = new AccuWeatherQuery.AccuWeatherQueryBuilder(
        accuWeatherApi.getHostName());
    accuWeatherQueryBuilder = accuWeatherQueryBuilder.locations()
        .version(accuWeatherApi.getVersion()).cities()
        .search().apiKey(accuWeatherApi.getApiKey());

    List<String> citiesKeys = Lists.newArrayList();

    for (String cityName : citiesNames) {
      AccuWeatherQuery accuWeatherQuery = accuWeatherQueryBuilder.query(cityName).build();
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> response = restTemplate
          .getForEntity(accuWeatherQuery.getUrl(), String.class);
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(response.getBody());
      JsonNode key = root.get(0).path("Key");
      citiesKeys.add(key.asText());
    }

    return ImmutableList.copyOf(citiesKeys);
  }

  private List<String> getFallback() {
    // simply failfast without trying to keep promise
    throw new AccuWeatherException();
  }
}
