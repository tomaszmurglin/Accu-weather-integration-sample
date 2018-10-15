
package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import lombok.Value;

@ThreadSafe
@Immutable
@Value
final class Metric {

  private final Double value;
  private final String unit;
  private final Integer unitType;
}
