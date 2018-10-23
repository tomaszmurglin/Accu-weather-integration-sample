package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Same cities have been requested to compare.")
public class SameCitiesRequestedException extends RuntimeException {

}
