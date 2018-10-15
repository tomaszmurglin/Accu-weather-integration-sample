
package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import lombok.Value;

@ThreadSafe
@Immutable
@Value
final class Temperature {

  private final Metric metric;
  private final Imperial imperial;
}
