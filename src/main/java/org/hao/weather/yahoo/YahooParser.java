package org.hao.weather.yahoo;

import java.io.InputStream;

import org.hao.weather.Weather;

/**
 * Parse an Yahoo Weather RSS response and return a Weather data.
 * 
 * @author hao
 *
 */
public interface YahooParser {
    /**
     *
     * @param inputStream input stream
     * @return Weather
     * @throws Exception
     */
    Weather parse(final InputStream inputStream) throws Exception;
}
