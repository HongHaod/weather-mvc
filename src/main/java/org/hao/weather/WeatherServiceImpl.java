package org.hao.weather;

import java.io.InputStream;

import org.hao.weather.yahoo.YahooParser;
import org.hao.weather.yahoo.YahooRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private YahooParser yahooParser;

    @Autowired
    private YahooRetriever yahooRetriever;

    public Weather retrieveWeather(final String locationCode) {
        try {
            final InputStream dataIn = yahooRetriever.retrieve(locationCode);
            final Weather weather = yahooParser.parse(dataIn);
            return weather;
        } catch (Exception e) {
            throw new WeatherServiceRuntimeException("Fail to get weather for location " + locationCode, e);
        }

    }
}
