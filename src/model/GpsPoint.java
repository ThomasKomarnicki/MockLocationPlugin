package model;

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
