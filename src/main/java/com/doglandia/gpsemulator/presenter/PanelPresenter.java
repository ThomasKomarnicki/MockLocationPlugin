package com.doglandia.gpsemulator.presenter;

import com.doglandia.gpsemulator.model.GpsEmulationModel;
import com.doglandia.gpsemulator.model.event.EmulationStartedEvent;
import com.doglandia.gpsemulator.model.event.EmulationStoppedEvent;
import com.doglandia.gpsemulator.service.EmulationService;
import com.doglandia.gpsemulator.service.ProgressCallback;
import com.doglandia.gpsemulator.ui.EmulationPanel;
import com.doglandia.gpsemulator.ui.MainToolWindow;
import com.intellij.openapi.components.ServiceManager;

/**
 * Created by Thomas on 12/22/2015.
 */
public class PanelPresenter implements ProgressCallback {

    private EmulationPanel currentEmulationPanel;
    private EmulationService emulationService;
    private MainToolWindow mainToolWindow;

    public PanelPresenter(MainToolWindow mainToolWindow){
        this.mainToolWindow = mainToolWindow;
        EmulationService.getBus().register(this);
        emulationService = ServiceManager.getService(EmulationService.class);
        emulationService.setProgressCallback(this);
    }

    public boolean emulationRunning() {
        return emulationService.isRunning();
    }

    public void setCurrentEmulationPanel(EmulationPanel currentEmulationPanel) {
        this.currentEmulationPanel = currentEmulationPanel;
    }

    public void onEmulationButtonClick(){
        if(emulationRunning()){
            emulationService.stopCurrentEmulation();
            mainToolWindow.onGpsEmulationStopped(new EmulationStoppedEvent());
        }else{
            if(currentEmulationPanel.validateData()){
                GpsEmulationModel gpsEmulationModel = currentEmulationPanel.createGpsEmulationData();
                if(gpsEmulationModel != null) {
                    int port = mainToolWindow.getPort();
                    if(port > 0) {
                        emulationService.startEmulation(gpsEmulationModel, port);
                        mainToolWindow.onGpsEmulationStarted(new EmulationStartedEvent());
                    }
                }

            }

        }
    }


    @Override
    public void onProgressEvent(int progress) {
        mainToolWindow.onProgressEvent(progress);
    }
}
