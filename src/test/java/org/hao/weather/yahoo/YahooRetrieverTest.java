package org.hao.weather.yahoo;

import static org.junit.Assert.*;

import org.junit.Test;

public class YahooRetrieverTest {

    private YahooRetriever yahooRetriever = new YahooRetrieverImpl();
    
    @Test
    public void shouldReturnWeatherInputStream() throws Exception {
        final String melbourneLocationCode = "ASXX0075";
        
        assertNotNull(yahooRetriever.retrieve(melbourneLocationCode));
    }

}
