package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = CitiesTooCloseToEachOtherException.REASON)
public class CitiesTooCloseToEachOtherException extends RuntimeException {

  static final String REASON = "Requested cities are too close to each other to compare them. ";

  public CitiesTooCloseToEachOtherException() {
    super(REASON + "Please provide correct cities names.");
  }
}
