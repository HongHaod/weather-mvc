package org.hao.weather;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
        "classpath:spring/root-context.xml" })
public class HomeControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Mock
    WeatherService weatherService;

    @InjectMocks
    HomeController homeController;
    
    @Resource
    private LinkedHashMap<String, String> cityLocationCodetoNameTable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        homeController.setCityLocationCodetoNameTable(cityLocationCodetoNameTable);
    }

    @Test
    public void shouldSetAllRequiredModelAndSessionAttributeWhenDoGetRequest() throws Exception {
        final String locationCodeMelbourne = "ASXX0075";
        final Weather retrievedWeather = new Weather();
        when(weatherService.retrieveWeather(locationCodeMelbourne)).thenReturn(retrievedWeather);

        final MvcResult result = this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name(containsString(ViewNames.home.name())))
                .andExpect(model().attribute(ModelAttributeNames.weather.name(),  sameInstance(retrievedWeather)))
                .andExpect(model().attribute(ModelAttributeNames.searchForm.name(), instanceOf(SearchForm.class)))
                .andExpect(model().attribute(ModelAttributeNames.serverTime.name(), instanceOf(String.class)))
                .andReturn();
        
        final HttpSession session = result.getRequest().getSession();
        assertNotNull(session.getAttribute(ModelAttributeNames.searchForm.name()));
    }
    
    @Test
    public void shouldSetAllRequiredModelAndSessionAttributeWhenDoPostRequest() throws Exception {
        final String sydneyLocationCode = "ASXX0112";
        final Weather retrievedWeather = new Weather();
        when(weatherService.retrieveWeather(sydneyLocationCode)).thenReturn(retrievedWeather);
        
        final MvcResult result = this.mockMvc
                .perform(post("/")
                .param("locationCode", sydneyLocationCode))             
                .andExpect(status().isOk())
                .andExpect(view().name(containsString(ViewNames.home.name())))
                .andExpect(model().attribute(ModelAttributeNames.weather.name(),  sameInstance(retrievedWeather)))
                .andExpect(model().attribute(ModelAttributeNames.searchForm.name(), instanceOf(SearchForm.class)))
                .andExpect(model().attribute(ModelAttributeNames.serverTime.name(), instanceOf(String.class)))
                .andReturn();
        
        final HttpSession session = result.getRequest().getSession();
        assertNotNull(session.getAttribute(ModelAttributeNames.searchForm.name()));
    }

    @Test
    public void shouldSetAllRequiredModelAndSessionAttributeWhenHandleException() throws Exception {
        final String sydneyLocationCode = "ASXX0112";
        final String exceptionMessage = "WeatherService error message";
        when(weatherService.retrieveWeather(sydneyLocationCode)).thenThrow(new WeatherServiceRuntimeException(exceptionMessage, null));
        
        final MvcResult result = this.mockMvc
                .perform(post("/")
                .param("locationCode", sydneyLocationCode))             
                .andExpect(status().isOk())
                .andExpect(view().name(containsString(ViewNames.home.name())))
                .andExpect(model().attribute(ModelAttributeNames.searchForm.name(), instanceOf(SearchForm.class)))
                .andExpect(model().attribute(ModelAttributeNames.serverTime.name(), instanceOf(String.class)))
                .andExpect(model().attribute(ModelAttributeNames.errorMessage.name(), equalTo(exceptionMessage)))
                .andReturn();
        
        final HttpSession session = result.getRequest().getSession();
        assertNotNull(session.getAttribute(ModelAttributeNames.searchForm.name()));
    }
}
