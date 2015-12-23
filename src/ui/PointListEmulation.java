package ui;

import com.intellij.openapi.components.ServiceManager;
import service.EmulationService;
import util.CardName;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 12/20/2015.
 */
public class PointListEmulation extends JPanel implements CardName {

    private JTextField startLocationLat;
    private JTextField startLocationLon;
    private JButton startGPSEmulationButton;
    private JTextField timeIntervalField;
    private JPanel pointListContent;
    private JCheckBox loopCheckBox;
    private JCheckBox reverseCheckBox;
    private JLabel errorText;


    public PointListEmulation(){

        startGPSEmulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                EmulationService emulationService = ServiceManager.getService(EmulationService.class);
                if (emulationService.isRunning()) {
                    emulationService.stopCurrentEmulation();
                } else {
                    errorText.setText("");
//                    if(validateInput()) {
//                        double startLat = Double.valueOf(startLocationLat.getText());
//
//                        double startLon = Double.valueOf(startLocationLon.getText());
//
//                        double endLat = Double.valueOf(endLocationLat.getText());
//                        double endLon = Double.valueOf(endLocationLon.getText());
//
//                        int steps = Integer.valueOf(stepsTextField.getText());
//                        int timeInterval = Integer.valueOf(timeIntervalField.getText());
//
//
//                        emulationService.setProgressCallback(progressCallback);
//                        emulationService.emulatePath(startLat, startLon, endLat, endLon, steps * timeInterval * 1000, steps);
//                    }

                }
            }
        });

    }

    @Override
    public String toString() {
        return "Point List Emulation";
    }

    @Override
    public String getCardName() {
        return "pointListEmulation";
    }
}
