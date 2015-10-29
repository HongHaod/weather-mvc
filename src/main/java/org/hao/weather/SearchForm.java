package org.hao.weather;

import java.util.Map;

public class SearchForm {

    private String locationCode;
    private Map<String, String> cityLocationCodetoNameTable;

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(final String locationCode) {
        this.locationCode = locationCode;
    }

    public Map<String, String> getCityLocationCodetoNameTable() {
        return cityLocationCodetoNameTable;
    }

    public void setCityLocationCodetoNameTable(final Map<String, String> cityLocationCodetoNameTable) {
        this.cityLocationCodetoNameTable = cityLocationCodetoNameTable;
    }
}
