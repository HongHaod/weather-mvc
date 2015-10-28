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
        
        final HttpSession session = request.getSession();
        
        // When first load the page, display weather for the first city in the configured list of cities
        final String firstLocationCode = cityLocationCodetoNameTable.keySet().iterator().next();
        SearchForm searchForm = new SearchForm();
        searchForm.setLocationCode(firstLocationCode);
        model.addAttribute(SessionAttributes.searchForm.name(), searchForm);
        session.setAttribute(SessionAttributes.searchForm.name(), searchForm);
        
        retrieveAndDisplayWeather(model, locale, searchForm.getLocationCode());

        return PageFlowAttributes.home.name();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String homeSubmit(@ModelAttribute SearchForm searchForm, Locale locale, Model model, final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        session.setAttribute(SessionAttributes.searchForm.name(), searchForm);
        retrieveAndDisplayWeather(model, locale, searchForm.getLocationCode());       
        return PageFlowAttributes.home.name();
    }
    
    @ExceptionHandler(WeatherServiceRuntimeException.class)
    public ModelAndView handleAllException(Exception ex, final HttpSession session) {
        ModelAndView model = new ModelAndView(PageFlowAttributes.home.name());
        model.addObject(SessionAttributes.errorMessage.name(), ex.getMessage());
        model.addObject(SessionAttributes.searchForm.name(), session.getAttribute(SessionAttributes.searchForm.name()));
        return model;
    }

    private void retrieveAndDisplayWeather(final Model model, final Locale locale, final String locationCode) {
        final Date now = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, locale);    
        final String formattedDate = sdf.format(now);
        model.addAttribute(SessionAttributes.serverTime.name(), formattedDate);       
        model.addAttribute(SessionAttributes.cityList.name(), cityLocationCodetoNameTable);
//        try {
            final Weather weather = weatherService.retrieveWeather(locationCode);
            model.addAttribute(SessionAttributes.weather.name(), weather);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//
//        }
    }
}
