package org.hao.weather;

public interface WeatherService {
    
    /**
     * retrieve weather for the give weather location code obtained from https://weather.codes/australia.
     *
     * @param locationCode weather location code
     * @return Weather
     * @throws Exception
     */
    Weather retrieveWeather(final String locationCode);
}
