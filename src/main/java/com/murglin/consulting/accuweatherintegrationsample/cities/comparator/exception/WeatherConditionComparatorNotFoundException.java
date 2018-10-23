package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = WeatherConditionComparatorNotFoundException.REASON)
public class WeatherConditionComparatorNotFoundException extends UnsupportedOperationException {

  static final String REASON = "Comparator for specified criteria has not been implemented yet. ";

  public WeatherConditionComparatorNotFoundException() {
    super(REASON + "Please contact with support team.");
  }
}
