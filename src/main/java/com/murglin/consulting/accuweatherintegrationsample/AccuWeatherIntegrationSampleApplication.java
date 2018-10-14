package com.murglin.consulting.accuweatherintegrationsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication(scanBasePackages = "com.murglin.consulting.accuweatherintegrationsample")
@EnableCircuitBreaker
public class AccuWeatherIntegrationSampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccuWeatherIntegrationSampleApplication.class, args);
  }
}
