package model;

import java.util.List;

/**
 * Created by Thomas on 12/23/2015.
 */
public class GpsEmulationModel {

    private List<GpsPoint> points;
    private int totalDuration; // in seconds

    public GpsEmulationModel(List<GpsPoint> points, int totalDuration) {
        this.points = points;
        this.totalDuration = totalDuration;
    }

    public List<GpsPoint> getPoints() {
        return points;
    }

    public int getTotalDuration() {
        return totalDuration;
    }
}
