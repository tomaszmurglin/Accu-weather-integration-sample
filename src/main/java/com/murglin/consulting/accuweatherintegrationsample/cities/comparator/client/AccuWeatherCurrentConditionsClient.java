package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception.AccuWeatherException;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.WeatherCondition;
import com.murglin.consulting.accuweatherintegrationsample.configuration.AccuWeatherConfig;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccuWeatherCurrentConditionsClient {

  private final AccuWeatherConfig accuWeatherConfig;

  @Autowired
  public AccuWeatherCurrentConditionsClient(AccuWeatherConfig accuWeatherConfig) {
    this.accuWeatherConfig = accuWeatherConfig;
  }

  @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand(fallbackMethod = "getFallback", threadPoolProperties = {
      @HystrixProperty(name = "maxQueueSize", value = "-1"),
      @HystrixProperty(name = "corePoolSize", value = "10")}, commandProperties = {
      @HystrixProperty(name = "circuitBreakerRequestVolumeThreshold", value = "5"),
      @HystrixProperty(name = "metricsRollingStatisticalWindowInMilliseconds", value = "5000")})
  public Collection<WeatherCondition> fetchCurrentWeatherConditionsForGivenCitiesKeys(
      Collection<String> citiesKeys) {
    var accuWeatherApi = accuWeatherConfig.getApi();

    AccuWeatherQuery.AccuWeatherQueryBuilder accuWeatherQueryBuilder = new AccuWeatherQuery.AccuWeatherQueryBuilder(
        accuWeatherApi.getHostName());
    accuWeatherQueryBuilder = accuWeatherQueryBuilder.currentConditions()
        .version(accuWeatherApi.getVersion());

    List<WeatherCondition> weatherConditions = Lists.newArrayList();

    for (String cityKey : citiesKeys) {
      AccuWeatherQuery accuWeatherQuery = accuWeatherQueryBuilder.param(cityKey)
          .apiKey(accuWeatherApi.getApiKey()).details(false).build();
      RestTemplate restTemplate = new RestTemplate();
      WeatherCondition weatherCondition = restTemplate
          .getForObject(accuWeatherQuery.getUrl(), WeatherCondition.class);
      weatherConditions.add(weatherCondition);
    }

    return ImmutableList.copyOf(weatherConditions);
  }

  private List<String> getFallback() {
    // simply failfast without trying to keep promise
    throw new AccuWeatherException();
  }

}
