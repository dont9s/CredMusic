
package com.gojek.weather.service.model.weather;

import com.google.gson.annotations.Expose;

public class Weather {

    @Expose
    private Current current;
    @Expose
    private Forecast forecast;
    @Expose
    private Location location;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
