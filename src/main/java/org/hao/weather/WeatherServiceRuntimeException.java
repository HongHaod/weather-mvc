package org.hao.weather;

public class WeatherServiceRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 999817865974017317L;

    public WeatherServiceRuntimeException(final String errorMessage, final Exception e) {
        super(errorMessage, e);
    }

}
