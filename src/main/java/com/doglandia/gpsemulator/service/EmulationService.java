package com.doglandia.gpsemulator.service;


import com.doglandia.gpsemulator.exception.AndroidConnectionException;
import com.doglandia.gpsemulator.model.GpsEmulationModel;
import com.doglandia.gpsemulator.model.GpsPoint;
import com.doglandia.gpsemulator.model.event.EmulationStoppedEvent;
import com.doglandia.gpsemulator.model.event.EmulatorErrorEvent;
import com.doglandia.gpsemulator.util.AndroidConsoleSession;
import com.google.common.eventbus.EventBus;
import com.intellij.openapi.application.ApplicationManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by Thomas on 12/19/2015.
 */
public class EmulationService {

    private static EventBus bus = new EventBus();

    private AndroidConsoleSession androidConsoleSession;

    private Future telnetFuture;

    private ProgressCallback progressCallback;

    public void startEmulation(final GpsEmulationModel gpsEmulationModel, final int port) {
        final List<GpsPoint> pathPoints = gpsEmulationModel.getPoints();

        if(telnetFuture != null && !telnetFuture.isDone()){
            telnetFuture.cancel(true);
        }

        telnetFuture = ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            @Override
            public void run() {
                long stepDuration = (gpsEmulationModel.getTotalDuration()*1000)/pathPoints.size();
                androidConsoleSession = new AndroidConsoleSession(port);
                try {
                    androidConsoleSession.startSession();
                    if(progressCallback != null){
                        progressCallback.onProgressEvent(0);
                    }
                    do {
                        for (int i = 0; i < pathPoints.size(); i++) {
                            GpsPoint gpsPoint = pathPoints.get(i);
                            androidConsoleSession.sendGpsCoords(gpsPoint.getLat(), gpsPoint.getLon());
                            if (progressCallback != null) {
                                progressCallback.onProgressEvent(100 * (i + 1) / (pathPoints.size()));
                            }
                            Thread.sleep(stepDuration);
                        }
                    }while(gpsEmulationModel.loop());
                    if(progressCallback != null){
                        progressCallback.onProgressEvent(100);
                    }
                    androidConsoleSession.endSession();
                    getBus().post(new EmulationStoppedEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                } catch (AndroidConnectionException e) {
//                    e.printStackTrace();
                    getBus().post(new EmulatorErrorEvent(e.getMessage()));
                }
            }

        });
    }


    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    public static EventBus getBus(){
        return bus;
    }

    public void stopCurrentEmulation() {
        if(telnetFuture != null && !telnetFuture.isDone()){
            telnetFuture.cancel(true);
        }
        getBus().post(new EmulationStoppedEvent());
    }

    public boolean isRunning(){
        return telnetFuture != null && !telnetFuture.isDone() && !telnetFuture.isCancelled();
    }
}
