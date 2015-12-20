package ui;

import com.google.common.eventbus.Subscribe;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import model.event.EmulationStartedEvent;
import model.event.EmulationStoppedEvent;
import org.apache.commons.lang.math.NumberUtils;
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

    /**
     * validates input and puts errors into errorText TextField
     * @return if any erros were found
     */
    private boolean validateInput(){
        String errors = "";
        boolean valid = true;

        String validateStartingLatResult = validateLat(startLocationLat.getText(), "Start Latitude");
        if(validateStartingLatResult != null){
            valid = false;
            errors += validateStartingLatResult;
        }

        String validateStartingLonResult = validateLon(startLocationLon.getText(), "Start Longitude");
        if(validateStartingLonResult != null){
            valid = false;
            errors += validateStartingLonResult;
        }

        String validateEndLatResult = validateLat(endLocationLat.getText(), "End Latitude");
        if(validateEndLatResult != null){
            valid = false;
            errors += validateEndLatResult;
        }

        String validateEndLonResult = validateLon(endLocationLon.getText(), "End Longitude");
        if(validateEndLonResult != null){
            valid = false;
            errors += validateEndLonResult;
        }

        try{
            Integer.valueOf(stepsTextField.getText());
        } catch (Exception e){
            valid = false;
            errors += "Steps must be a valid Integer<br>";
        }

        try{
            Integer.valueOf(timeIntervalField.getText());
        } catch (Exception e){
            valid = false;
            errors += "Time Interval must be a valid Integer<br>";
        }

        if(!valid){
            errorText.setText("<html>"+errors+"</html>");

        }

        return valid;
    }

    /**
     *
     * @param lat
     * @param latName
     * @return error string if the entered latitude is not valid
     */
    private String validateLat(String lat, String latName){
        if(NumberUtils.isNumber(lat)){
            double startLat = Double.valueOf(lat);
            if(startLat < -90 || startLat > 90){
                return latName + " must be a valid number between -90 and 90<br>";
            }
        }else{
            return latName + " must be a valid number between -90 and 90<br>";
        }
        return null;
    }

    /**
     *
     * @param lon
     * @param lonName
     * @return error string is enerted longitude is not valid
     */
    private String validateLon(String lon, String lonName){
        if(NumberUtils.isNumber(lon)){
            double startLat = Double.valueOf(lon);
            if(startLat < -90 || startLat > 90){
                return lonName + " must be a valid number between -180 and 180<br>";
            }
        }else{
            return lonName + " must be a valid number between -180 and 180<br>";
        }
        return null;
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
