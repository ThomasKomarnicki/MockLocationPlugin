package com.doglandia.gpsemulator.ui;


import com.doglandia.gpsemulator.model.GpsEmulationModel;
import com.doglandia.gpsemulator.presenter.PanelPresenter;

/**
 * Created by Thomas on 12/22/2015.
 */
public interface EmulationPanel {

//    void updateProgress(int progress); // out of 100

//    void onGpsEmulationStopped();
//    void onGpsEmulationStarted();

    GpsEmulationModel createGpsEmulationData();

    boolean validateData();

    void setPanelPresenter(PanelPresenter panelPresenter);
}
