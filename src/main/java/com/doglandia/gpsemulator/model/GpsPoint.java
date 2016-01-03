package com.doglandia.gpsemulator.model;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Thomas on 12/19/2015.
 */
public class GpsPoint {

    private double lat;
    private double lon;

    public GpsPoint(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public GpsPoint(@NotNull  String lat, @NotNull String lon) {
        this(Double.valueOf(lat.trim()),Double.valueOf(lon.trim()));
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return lon + " " + lat;
    }
}
