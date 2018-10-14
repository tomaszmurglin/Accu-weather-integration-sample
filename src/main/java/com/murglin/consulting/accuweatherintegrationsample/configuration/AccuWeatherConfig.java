package com.murglin.consulting.accuweatherintegrationsample.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "accu-weather")
public class AccuWeatherConfig {

  private Api api;

  public Api getApi() {
    return api;
  }

  public void setApi(
      Api api) {
    this.api = api;
  }

  public static class Api {

    private String hostName;

    private String apiKey;

    private String version;

    public String getVersion() {
      return version;
    }

    public void setVersion(String version) {
      this.version = version;
    }

    public String getApiKey() {
      return apiKey;
    }

    public void setApiKey(String apiKey) {
      this.apiKey = apiKey;
    }

    public String getHostName() {
      return hostName;
    }

    public void setHostName(String hostName) {
      this.hostName = hostName;
    }
  }
}
