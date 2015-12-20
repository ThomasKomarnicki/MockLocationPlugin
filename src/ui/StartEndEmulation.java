package ui;

import com.google.common.eventbus.Subscribe;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import model.event.EmulationStartedEvent;
import model.event.EmulationStoppedEvent;
import service.EmulationService;
import service.ProgressCallback;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 12/20/2015.
 */
public class StartEndEmulation {

    private JPanel gpsPathPanel;
    private JTextField startLocationLat;
    private JTextField startLocationLon;
    private JButton startGPSEmulationButton;
    private JTextField stepsTextField;
    private JTextField timeIntervalField;
    private JTextField endLocationLat;
    private JTextField endLocationLon;
    private JPanel startEndRootJpanel;
    private JLabel errorText;

    private ProgressCallback progressCallback;

    private boolean emulationRunning = false;

    public StartEndEmulation(){

        EmulationService.getBus().register(this);

        startGPSEmulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                EmulationService emulationService = ServiceManager.getService(EmulationService.class);
                if (emulationRunning) {
                    emulationService.stopCurrentEmulation();
                } else {
                    try {
                        double startLat = Double.valueOf(startLocationLat.getText());
                        double startLon = Double.valueOf(startLocationLon.getText());

                        double endLat = Double.valueOf(endLocationLat.getText());
                        double endLon = Double.valueOf(endLocationLon.getText());

                        int steps = Integer.valueOf(stepsTextField.getText());
                        int timeInterval = Integer.valueOf(timeIntervalField.getText());


                        emulationService.setProgressCallback(progressCallback);
                        emulationService.emulatePath(startLat, startLon, endLat, endLon, steps * timeInterval * 1000, steps);


                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        EmulationService.getBus().unregister(this);
        super.finalize();
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    @Override
    public String toString() {
        return "Straight Line GPS Emulator";
    }

    @Subscribe
    public void onGpsEmulationStopped(EmulationStoppedEvent emulationStoppedEvent){
        emulationRunning = false;
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                startGPSEmulationButton.setText("Start GPS Emulation");
            }
        });

    }

    @Subscribe
    public void onGpsEmulationStarted(EmulationStartedEvent emulationStartedEvent){
        emulationRunning = true;
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                startGPSEmulationButton.setText("Stop GPS Emulation");
            }
        });

    }
}
