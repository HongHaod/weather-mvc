package org.hao.weather.yahoo;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "/spring/root-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class YahooRetrieverTest {

    @Autowired
    private YahooRetriever yahooRetriever;
    
    @Test
    public void shouldReturnWeatherInputStream() throws Exception {
        final String melbourneLocationCode = "ASXX0075";
        
        assertNotNull(yahooRetriever.retrieve(melbourneLocationCode));
    }

}
