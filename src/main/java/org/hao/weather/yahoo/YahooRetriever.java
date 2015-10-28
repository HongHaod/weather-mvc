package org.hao.weather.yahoo;

import java.io.InputStream;

/**
 * Retrieve Yahoo Weather as an input stream.
 *
 * @author hao
 *
 */
public interface YahooRetriever {

    String YAHOOAPI_WEATHER_URL = "http://weather.yahooapis.com/forecastrssXX?p=";
    String TEMPARATURE_CELSIUS_CODE = "u=c";

    InputStream retrieve(final String locationCode) throws Exception;

}