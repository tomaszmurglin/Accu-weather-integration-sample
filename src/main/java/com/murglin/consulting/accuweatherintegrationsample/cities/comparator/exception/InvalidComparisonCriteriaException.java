package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid comparison criteria")
public class InvalidComparisonCriteriaException extends RuntimeException {

}
