package service;


import com.google.common.eventbus.EventBus;
import com.intellij.openapi.application.ApplicationManager;
import model.GpsEmulationModel;
import model.GpsPoint;
import model.event.EmulationStoppedEvent;
import util.TelnetSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by Thomas on 12/19/2015.
 */
public class EmulationService {

    private static EventBus bus = new EventBus();

    private TelnetSession telnetSession;

    private Future telnetFuture;

    private ProgressCallback progressCallback;

    public void startEmulation(final GpsEmulationModel gpsEmulationModel){
        final List<GpsPoint> pathPoints = gpsEmulationModel.getPoints();

        if(telnetFuture != null && !telnetFuture.isDone()){
            telnetFuture.cancel(true);
        }

        telnetFuture = ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            @Override
            public void run() {
                long stepDuration = gpsEmulationModel.getTotalDuration()/pathPoints.size();
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
                    getBus().post(new EmulationStoppedEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
//                    e.printStackTrace();
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
