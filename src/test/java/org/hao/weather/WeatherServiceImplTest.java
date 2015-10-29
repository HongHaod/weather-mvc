package org.hao.weather;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.hao.weather.yahoo.YahooParser;
import org.hao.weather.yahoo.YahooRetriever;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "/spring/root-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class WeatherServiceImplTest {

    @Mock
    private YahooParser yahooParser;

    @Mock
    private YahooRetriever yahooRetriever;
    
    @InjectMocks
    private WeatherServiceImpl weatherService;   

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnWeatherWhenRetrieveFromYahooRetriever() throws Exception {
        // setup data
        final String zipCode = "ASXX0075";
        final Weather weather = new Weather();
        
        // mock yahoo retrieve weather
        InputStream inputStream = mock(InputStream.class);
        when(yahooRetriever.retrieve(zipCode)).thenReturn(inputStream);       
        when(yahooParser.parse(inputStream)).thenReturn(weather);
        
        // verify result
        assertSame(weather, weatherService.retrieveWeather(zipCode));
    }
    
    @Test(expected=WeatherServiceRuntimeException.class)
    public void shouldThrowExceptionWhenYahooRetrieverThrowException() throws Exception {
        // setup data
        final String zipCode = "ASXX0075";
        
        // mock yahoo retrieve weather
        when(yahooRetriever.retrieve(zipCode)).thenThrow(new IOException("Some Error"));
        
        weatherService.retrieveWeather(zipCode);
    }

}
