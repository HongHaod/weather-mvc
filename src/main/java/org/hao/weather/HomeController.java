package org.hao.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private static final String DATE_TIME_FORMAT = "EEEE hh:mm a";

    @Autowired
    private WeatherService weatherService;

    @Resource
    private LinkedHashMap<String, String> cityLocationCodetoNameTable;

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(final Locale locale, final Model model, final HttpServletRequest request) {
        logger.info("Welcome home! The client locale is {}.", locale);

        final SearchForm searchForm = createSearchForm();
        model.addAttribute(ModelAttributeNames.searchForm.name(), searchForm);
        request.getSession().setAttribute(ModelAttributeNames.searchForm.name(), searchForm);

        retrieveAndDisplayWeather(model, locale, searchForm.getLocationCode());

        return ViewNames.home.name();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String homeSubmit(@ModelAttribute SearchForm searchForm, Locale locale, Model model,
            final HttpSession session) {
        searchForm.setCityLocationCodetoNameTable(cityLocationCodetoNameTable);
        session.setAttribute(ModelAttributeNames.searchForm.name(), searchForm);
        retrieveAndDisplayWeather(model, locale, searchForm.getLocationCode());
        return ViewNames.home.name();
    }

    @ExceptionHandler(WeatherServiceRuntimeException.class)
    public ModelAndView handleWeatherServiceException(Exception ex, final HttpSession session, final Locale locale) {
        ModelAndView model = new ModelAndView(ViewNames.home.name());
        model.addObject(ModelAttributeNames.serverTime.name(), getServerTime(locale));
        model.addObject(ModelAttributeNames.errorMessage.name(), ex.getMessage());
        final SearchForm searchForm = (SearchForm)session.getAttribute(ModelAttributeNames.searchForm.name());
        searchForm.setCityLocationCodetoNameTable(cityLocationCodetoNameTable);
        model.addObject(ModelAttributeNames.searchForm.name(), searchForm);
        return model;
    }

    public void setCityLocationCodetoNameTable(LinkedHashMap<String, String> cityLocationCodetoNameTable) {
        this.cityLocationCodetoNameTable = cityLocationCodetoNameTable;
    }
    
    private void retrieveAndDisplayWeather(final Model model, final Locale locale, final String locationCode) {
        model.addAttribute(ModelAttributeNames.serverTime.name(), getServerTime(locale));

        final Weather weather = weatherService.retrieveWeather(locationCode);
        model.addAttribute(ModelAttributeNames.weather.name(), weather);
    }

    private String getServerTime(final Locale locale) {
        final Date now = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, locale);
        final String formattedDate = sdf.format(now);
        return formattedDate;
    }
    
    private SearchForm createSearchForm() {
        // When first load the page, display weather for the first city in the configured list of cities
        final String firstLocationCode = cityLocationCodetoNameTable.keySet().iterator().next();
        SearchForm searchForm = new SearchForm();
        searchForm.setLocationCode(firstLocationCode);
        searchForm.setCityLocationCodetoNameTable(cityLocationCodetoNameTable);
        return searchForm;
    }

}
