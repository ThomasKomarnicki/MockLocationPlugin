package service;


import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import model.GpsPoint;
import util.TelnetSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by Thomas on 12/19/2015.
 */
public class EmulationService {

    private TelnetSession telnetSession;

    private Future telnetFuture;

    private ProgressCallback progressCallback;

    public void emulatePath(double startLat, double startLon, double endLat, double endLon, int timeInterval, int steps){

        double latDiff = startLat - endLat;
        double lonDiff = startLon - endLon;

        double latStep = latDiff / steps;
        double lonStep = lonDiff / steps;

        List<GpsPoint> points = new ArrayList<>();
        for(int i = 0; i < steps-1; i++){
            points.add(new GpsPoint(startLat - (i*latStep), startLon -(i * lonStep)));
        }
        points.add(new GpsPoint(endLat, endLon));

        startPointEmulation(points, timeInterval);

    }

    private void startPointEmulation(final List<GpsPoint> pathPoints, final int totalDuration){
        if(telnetFuture != null && !telnetFuture.isDone()){
            telnetFuture.cancel(true);
        }

        telnetFuture = ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            @Override
            public void run() {
                long stepDuration = totalDuration/pathPoints.size();
                telnetSession = new TelnetSession();
                try {
                    telnetSession.startSession();
                    if(progressCallback != null){
                        progressCallback.onProgressEvent(0);
                    }
                    for(int i = 0; i < pathPoints.size(); i++){
                        GpsPoint gpsPoint = pathPoints.get(i);
                        telnetSession.sendGpsCoords(gpsPoint.getLat(), gpsPoint.getLon());
                        Thread.sleep(stepDuration);
                        if(progressCallback != null){
                            progressCallback.onProgressEvent(100*(i+1)/(pathPoints.size()));
                        }
                    }
                    if(progressCallback != null){
                        progressCallback.onProgressEvent(100);
                    }
                    telnetSession.endSession();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                }
            }

        });
//        telnetThread.start();

    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }
}
