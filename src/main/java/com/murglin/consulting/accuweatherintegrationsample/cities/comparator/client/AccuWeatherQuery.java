package com.murglin.consulting.accuweatherintegrationsample.cities.comparator.client;

class AccuWeatherQuery {

  private static final String LOCATIONS = "locations";

  private static final String CURRENT_CONDITIONS = "currentconditions";

  private static final String CITIES = "cities";

  private static final String SEARCH = "search?";

  private static final String API_KEY = "apikey";

  private static final String QUERY_PARAM = "&q";

  private static final String DETAILS = "details";

  private final String url;

  private AccuWeatherQuery(String url) {
    this.url = url;
  }

  String getUrl() {
    return url;
  }

  static class AccuWeatherQueryBuilder {

    private String url;

    AccuWeatherQueryBuilder(String hostName) {
      this.url = hostName;
    }

    private void setUrl(String url) {
      this.url = url;
    }

    private void appendUrl(String value) {
      String delimiter = url.endsWith("?") ? "" : "/";
      setUrl(url + delimiter + value);
    }

    AccuWeatherQueryBuilder locations() {
      appendUrl(LOCATIONS);
      return this;
    }

    AccuWeatherQueryBuilder version(String version) {
      appendUrl(version);
      return this;
    }

    AccuWeatherQueryBuilder cities() {
      appendUrl(CITIES);
      return this;
    }

    AccuWeatherQueryBuilder search() {
      appendUrl(SEARCH);
      return this;
    }

    AccuWeatherQueryBuilder apiKey(String apiKey) {
      appendUrl(API_KEY + "=" + apiKey);
      return this;
    }

    AccuWeatherQueryBuilder query(String queryParam) {
      appendUrl(QUERY_PARAM + "=" + queryParam);
      return this;
    }

    AccuWeatherQueryBuilder currentConditions() {
      appendUrl(CURRENT_CONDITIONS);
      return this;
    }

    AccuWeatherQueryBuilder details(boolean enabled) {
      appendUrl(DETAILS + "=" + enabled);
      return this;
    }

    AccuWeatherQueryBuilder param(String param) {
      appendUrl(param);
      return this;
    }

    AccuWeatherQuery build() {
      return new AccuWeatherQuery(url);
    }
  }
}
