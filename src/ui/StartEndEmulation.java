package ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import service.EmulationService;
import service.ProgressCallback;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 12/20/2015.
 */
public class StartEndEmulation extends JPanel{
    private JPanel gpsPathPanel;
    private JTextField startLocationLat;
    private JTextField startLocationLon;
    private JButton startGPSEmulationButton;
    private JTextField stepsTextField;
    private JTextField timeIntervalField;
    private JTextField endLocationLat;
    private JTextField endLocationLon;

    private ProgressCallback progressCallback;

    public StartEndEmulation(){

        startGPSEmulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    double startLat = Double.valueOf(startLocationLat.getText());
                    double startLon = Double.valueOf(startLocationLon.getText());

                    double endLat = Double.valueOf(endLocationLat.getText());
                    double endLon = Double.valueOf(endLocationLon.getText());

                    int steps = Integer.valueOf(stepsTextField.getText());
                    int timeInterval = Integer.valueOf(timeIntervalField.getText());

                    EmulationService emulationService = ServiceManager.getService(EmulationService.class);
                    emulationService.setProgressCallback(progressCallback);
                    emulationService.emulatePath(startLat, startLon, endLat, endLon, steps * timeInterval * 1000, steps);

                } catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }
}
