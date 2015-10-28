package org.hao.weather.yahoo;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hao.weather.Weather;
import org.junit.Test;

public class YahooParserTest {

    private YahooParser yahooParser = new YahooParserImpl();

    @Test
    public void shouldReturnCorrectWeatherDetailsWhenParseXmlWeatherFile() throws Exception {
        final String yahooXmlResponseFile = "ny-weather.xml";      
        final InputStream nyData = getClass().getClassLoader().getResourceAsStream(yahooXmlResponseFile);
        final Weather weather = yahooParser.parse(nyData);
        
        assertEquals("New York", weather.getCity());
        assertEquals("NY", weather.getRegion());
        assertEquals("US", weather.getCountry());
        assertEquals("39", weather.getTemperature());
        assertEquals("Fair", weather.getCondition());
        assertEquals("9", weather.getWindSpeed());
        
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE hh:mm a");    
        final String formattedDate = sdf.format(new Date());
        System.out.println(formattedDate);
    }

}
