package org.hao.weather.yahoo;

import java.io.InputStream;

/**
 * Retrieve Yahoo Weather as an input stream.
 *
 * @author hao
 *
 */
public interface YahooRetriever {

    String TEMPARATURE_CELSIUS_CODE = "u=c";

    InputStream retrieve(final String locationCode) throws Exception;

}