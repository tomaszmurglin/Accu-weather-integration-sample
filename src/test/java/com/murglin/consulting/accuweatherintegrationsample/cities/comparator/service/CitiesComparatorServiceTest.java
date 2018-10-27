package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client.AccuWeatherCurrentConditionsClient;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client.AccuWeatherLocationsClient;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.exception.WeatherConditionComparatorNotFoundException;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.ComparisonCriteria;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.Imperial;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.Metric;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.Temperature;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.WeatherCondition;
import com.murglin.consulting.accuweatherintegrationsample.cities.comparator.model.dto.WeatherConditionDTO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
class CitiesComparatorServiceTest {

  @MockBean
  private AccuWeatherLocationsClient accuWeatherLocationsClient;

  @MockBean
  private AccuWeatherCurrentConditionsClient accuWeatherCurrentConditionsClient;

  @Autowired
  private CitiesComparatorService citiesComparatorService;

  private static final String GDANSK = "Gdansk";
  private static final String WARSZAWA = "Warszawa";
  private final static Set<String> CITIES_NAMES = Set.of(GDANSK, WARSZAWA);
  private final static Map<String, String> CITIES_NAMES_TO_LOCATION_KEYS = Map
      .of(GDANSK, UUID.randomUUID().toString(), WARSZAWA, UUID.randomUUID().toString());
  private static final WeatherCondition WEATHER_IN_GDANSK = new WeatherCondition();
  private static final WeatherCondition WEATHER_IN_WARSAW = new WeatherCondition();

  static {
    Random random = new Random();

    WEATHER_IN_GDANSK.setEpochTime(random.nextInt());
    WEATHER_IN_GDANSK.setIsDayTime(true);
    WEATHER_IN_GDANSK.setLocalObservationDateTime(UUID.randomUUID().toString());
    Temperature temperatureInGdansk = new Temperature();
    Imperial imperialInGdansk = new Imperial();
    imperialInGdansk.setUnit(UUID.randomUUID().toString());
    imperialInGdansk.setUnitType(random.nextInt());
    imperialInGdansk.setValue(3);
    temperatureInGdansk.setImperial(imperialInGdansk);
    Metric metricInGdansk = new Metric();
    metricInGdansk.setUnit(UUID.randomUUID().toString());
    metricInGdansk.setUnitType(random.nextInt());
    metricInGdansk.setValue(3.0);
    temperatureInGdansk.setMetric(metricInGdansk);
    WEATHER_IN_GDANSK.setTemperature(temperatureInGdansk);
    WEATHER_IN_GDANSK.setWeatherIcon(random.nextInt());
    WEATHER_IN_GDANSK.setWeatherText(UUID.randomUUID().toString());

    WEATHER_IN_WARSAW.setEpochTime(random.nextInt());
    WEATHER_IN_WARSAW.setIsDayTime(true);
    WEATHER_IN_WARSAW.setLocalObservationDateTime(UUID.randomUUID().toString());
    Temperature temperatureInWarsaw = new Temperature();
    Imperial imperialInWarsaw = new Imperial();
    imperialInWarsaw.setUnit(UUID.randomUUID().toString());
    imperialInWarsaw.setUnitType(random.nextInt());
    imperialInWarsaw.setValue(2);
    temperatureInWarsaw.setImperial(imperialInWarsaw);
    Metric metricInWarsaw = new Metric();
    metricInWarsaw.setUnit(UUID.randomUUID().toString());
    metricInWarsaw.setUnitType(random.nextInt());
    metricInWarsaw.setValue(2.0);
    temperatureInWarsaw.setMetric(metricInWarsaw);
    WEATHER_IN_WARSAW.setTemperature(temperatureInWarsaw);
    WEATHER_IN_WARSAW.setWeatherIcon(random.nextInt());
    WEATHER_IN_WARSAW.setWeatherText(UUID.randomUUID().toString());
  }

  private static final Map<String, WeatherCondition> CITIES_NAMES_TO_WEATHER = Map
      .of(GDANSK, WEATHER_IN_GDANSK, WARSZAWA, WEATHER_IN_WARSAW);

  @BeforeEach
  void mock() throws IOException {
    Mockito.when(accuWeatherLocationsClient.fetchLocationKeysForGivenCitiesNames(CITIES_NAMES))
        .thenReturn(CITIES_NAMES_TO_LOCATION_KEYS);

    Mockito
        .when(accuWeatherCurrentConditionsClient
            .fetchCurrentWeatherConditionsForGivenCitiesKeys(CITIES_NAMES_TO_LOCATION_KEYS))
        .thenReturn(CITIES_NAMES_TO_WEATHER);
  }

  @Test
  void shouldReturnCitiesOrderedByTemperature() throws IOException {
    //given mocked
    //when
    List<WeatherConditionDTO> weatherConditionDTOS = citiesComparatorService
        .compareCitiesByWeatherConditions(CITIES_NAMES, ComparisonCriteria.TEMPERATURE);
    //then
    assertEquals(List.of(new WeatherConditionDTO(GDANSK, WEATHER_IN_GDANSK),
        new WeatherConditionDTO(WARSZAWA, WEATHER_IN_WARSAW)), weatherConditionDTOS);
  }

  @Test
  void shouldThrowExceptionIfComparatorNotFound() {
    //given mocked
    //when
    Throwable runtimeException = assertThrows(WeatherConditionComparatorNotFoundException.class,
        () -> {
          ComparisonCriteria NOT_IMPLEMENTED_COMPARISON_CRITERIA = PowerMockito.mock(MyEnum.class);
          Whitebox.setInternalState(C, "name", "C");
          Whitebox.setInternalState(C, "ordinal", 2);

          PowerMockito.mockStatic(MyEnum.class);

          citiesComparatorService
              .compareCitiesByWeatherConditions(CITIES_NAMES, ComparisonCriteria.TEMPERATURE);
        });
    //then
    assertEquals(
        "Comparator for specified criteria has not been implemented yet. Please contact with support team.",
        runtimeException.getMessage());
  }
}
