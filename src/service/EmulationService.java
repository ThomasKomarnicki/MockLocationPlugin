package service;


import model.GpsPoint;
import util.TelnetSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 12/19/2015.
 */
public class EmulationService {

    private TelnetSession telnetSession;

    private Thread telnetThread;

    public void emulatePath(double startLat, double startLon, double endLat, double endLon, int timeInterval, int steps){

        double latDiff = startLat - endLat;
        double lonDiff = startLon - endLon;

        double latStep = latDiff / steps;
        double lonStep = lonDiff / steps;

        List<GpsPoint> points = new ArrayList<>();
        for(int i = 0; i < steps; i++){
            points.add(new GpsPoint(startLat + (i*latStep), startLon +(i * lonStep)));
        }

        startPointEmulation(points, timeInterval);

    }

    private void startPointEmulation(final List<GpsPoint> pathPoints, final int totalDuration){
        if(telnetThread != null){
            telnetThread.interrupt();
        }
        telnetThread = new Thread(new Runnable() {
            @Override
            public void run() {
                long stepDuration = totalDuration/pathPoints.size();
                telnetSession = new TelnetSession();
                try {
                    telnetSession.startSession();
                    for(GpsPoint gpsPoint : pathPoints){
                        telnetSession.sendGpsCoords(gpsPoint.getLat(), gpsPoint.getLon());
                        Thread.sleep(stepDuration);
                    }
                    telnetSession.endSession();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        telnetThread.start();

    }


}
