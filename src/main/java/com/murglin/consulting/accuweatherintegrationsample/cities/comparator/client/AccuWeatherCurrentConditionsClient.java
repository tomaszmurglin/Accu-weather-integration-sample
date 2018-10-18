package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception.AccuWeatherServiceNotAvailableException;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.WeatherCondition;
import com.murglin.consulting.accuweatherintegrationsample.configuration.AccuWeatherConfig;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AccuWeatherCurrentConditionsClient {

  private final AccuWeatherConfig accuWeatherConfig;

  @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand(fallbackMethod = "getFallback", threadPoolProperties = {
      @HystrixProperty(name = "maxQueueSize", value = "-1"),
      @HystrixProperty(name = "coreSize", value = "10")}, commandProperties = {
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "5000")})
  private WeatherCondition fetchCurrentWeatherConditionForGivenCitiesKey(String cityKey,
      AccuWeatherQuery.AccuWeatherQueryBuilder accuWeatherQueryBuilder) {
    var accuWeatherApi = accuWeatherConfig.getApi();
    AccuWeatherQuery accuWeatherQuery = accuWeatherQueryBuilder.param(cityKey)
        .apiKey(accuWeatherApi.getApiKey()).details(false).build();
    RestTemplate restTemplate = new RestTemplate();
    WeatherCondition[] weatherCondition = restTemplate
        .getForObject(accuWeatherQuery.getUrl(), WeatherCondition[].class);
    return weatherCondition[0];
  }

  public List<WeatherCondition> fetchCurrentWeatherConditionsForGivenCitiesKeys(
      Set<String> citiesKeys) {
    var accuWeatherApi = accuWeatherConfig.getApi();

    AccuWeatherQuery.AccuWeatherQueryBuilder accuWeatherQueryBuilder = new AccuWeatherQuery.AccuWeatherQueryBuilder(
        accuWeatherApi.getHostName());
    accuWeatherQueryBuilder = accuWeatherQueryBuilder.currentConditions()
        .version(accuWeatherApi.getVersion());

    List<WeatherCondition> weatherConditions = Lists.newArrayList();

    for (String cityKey : citiesKeys) {
      WeatherCondition weatherCondition = fetchCurrentWeatherConditionForGivenCitiesKey(cityKey,
          accuWeatherQueryBuilder);
      weatherConditions.add(weatherCondition);
    }

    return ImmutableList.copyOf(weatherConditions);
  }

  private List<WeatherCondition> getFallback(Set<String> citiesKeys, Throwable reason) {
    // simply failfast without trying to keep promise
    throw new AccuWeatherServiceNotAvailableException();
  }

}
