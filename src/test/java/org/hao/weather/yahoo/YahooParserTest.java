package org.hao.weather.yahoo;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hao.weather.Weather;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "/spring/root-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class YahooParserTest {

    @Autowired
    private YahooParser yahooParser;

    @Test
    public void shouldReturnCorrectWeatherDetailsWhenParseXmlWeatherFile() throws Exception {
        final String yahooXmlResponseFile = "ny-weather.xml";      
        final InputStream nyData = getClass().getClassLoader().getResourceAsStream(yahooXmlResponseFile);
        final Weather weather = yahooParser.parse(nyData);
        
        assertEquals("New York", weather.getCity());
        assertEquals("39", weather.getTemperature());
        assertEquals("Fair", weather.getCondition());
        assertEquals("9", weather.getWindSpeed());
        
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE hh:mm a");    
        final String formattedDate = sdf.format(new Date());
        System.out.println(formattedDate);
    }

}
