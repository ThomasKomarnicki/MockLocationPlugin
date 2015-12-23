package ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import controller.ViewController;
import service.EmulationService;
import util.CardName;
import util.DataValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 12/20/2015.
 */
public class StartEndEmulationPanel extends JPanel implements CardName, EmulationPanel{

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

//    private ProgressCallback progressCallback;

//    private boolean emulationRunning = false;

    private ViewController viewController;

    private DataValidator dataValidator;

    public StartEndEmulationPanel(){

        dataValidator = new DataValidator();

        startGPSEmulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                EmulationService emulationService = ServiceManager.getService(EmulationService.class);
                if (viewController.emulationRunning()) {
                    emulationService.stopCurrentEmulation();
                } else {
                    errorText.setText("");
                    if(validateInput()) {
                        double startLat = Double.valueOf(startLocationLat.getText());

                        double startLon = Double.valueOf(startLocationLon.getText());

                        double endLat = Double.valueOf(endLocationLat.getText());
                        double endLon = Double.valueOf(endLocationLon.getText());

                        int steps = Integer.valueOf(stepsTextField.getText());
                        int timeInterval = Integer.valueOf(timeIntervalField.getText());


                        emulationService.setProgressCallback(progressCallback);
                        emulationService.emulatePath(startLat, startLon, endLat, endLon, steps * timeInterval * 1000, steps);
                    }

                }
            }
        });
    }

    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }



    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

//    @Subscribe
//    public void onGpsEmulationStopped(EmulationStoppedEvent emulationStoppedEvent){
//
//
//    }
//
//    @Subscribe
//    public void onGpsEmulationStarted(EmulationStartedEvent emulationStartedEvent){
//
//
//    }

    @Override
    public String getCardName() {
        return "startEndEmulationPanel";
    }


    @Override
    public String toString() {
        return "Straight Line GPS Emulator";
    }

    @Override
    public void onGpsEmulationStopped() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                startGPSEmulationButton.setText("Start GPS Emulation");
            }
        });
    }

    @Override
    public void onGpsEmulationStarted() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                startGPSEmulationButton.setText("Stop GPS Emulation");
            }
        });
    }
}
