package org.hao.weather;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "/spring/root-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class WeatherServiceImplTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    public void test() throws Exception {
        final String zipCode = "ASXX0075";
        final Weather weather = weatherService.retrieveWeather(zipCode);
        assertEquals("Melbourne", weather.getCity());
    }

}
