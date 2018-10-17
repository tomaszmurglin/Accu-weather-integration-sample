package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalDefaultExceptionHandler {

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = Exception.class)
  public void
  defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    /* If the exception is annotated with @ResponseStatus rethrow it and let
     the framework handle it
     */
    if (AnnotationUtils.findAnnotation
        (e.getClass(), ResponseStatus.class) != null) {
      throw e;
    }
  }

}
