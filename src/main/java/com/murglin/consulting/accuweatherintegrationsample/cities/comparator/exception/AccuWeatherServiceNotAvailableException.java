package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "AccuWeather web service is not available.")
public class AccuWeatherServiceNotAvailableException extends RuntimeException {

  public AccuWeatherServiceNotAvailableException() {
    super("Weather information is currently not available. Please try again after few seconds.");
  }
}
