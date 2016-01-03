package com.doglandia.gpsemulator.model;

import java.util.List;

/**
 * Created by Thomas on 12/23/2015.
 */
public class GpsEmulationModel {

    private List<GpsPoint> points;
    private int totalDuration; // in seconds
    private boolean loop;

    public GpsEmulationModel(List<GpsPoint> points, int totalDuration) {
        this.points = points;
        this.totalDuration = totalDuration;
        this.loop = false;
    }

    public GpsEmulationModel(List<GpsPoint> points, int totalDuration, boolean loop) {
        this.points = points;
        this.totalDuration = totalDuration;
        this.loop = loop;
    }

    public List<GpsPoint> getPoints() {
        return points;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public boolean loop() {
        return loop;
    }
}
