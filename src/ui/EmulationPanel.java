package ui;

import model.GpsEmulationModel;
import model.GpsPoint;
import presenter.Presenter;

import java.util.List;

/**
 * Created by Thomas on 12/22/2015.
 */
public interface EmulationPanel {

//    void updateProgress(int progress); // out of 100

    void onGpsEmulationStopped();
    void onGpsEmulationStarted();

    GpsEmulationModel createGpsEmulationData();

    boolean validateData();

    void setPresenter(Presenter presenter);
}
