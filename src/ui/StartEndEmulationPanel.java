package ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import dataValidation.ValidationResult;
import model.GpsEmulationModel;
import model.GpsPoint;
import presenter.Presenter;
import service.EmulationService;
import util.CardName;
import dataValidation.DataValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

    private Presenter presenter;

    private DataValidator dataValidator;

    public StartEndEmulationPanel(){

        dataValidator = new DataValidator();

        startGPSEmulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                presenter.onEmulationButtonClick();
            }
        });
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }


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

    @Override
    public GpsEmulationModel createGpsEmulationData() {
        double startLat = Double.valueOf(startLocationLat.getText());

        double startLon = Double.valueOf(startLocationLon.getText());

        double endLat = Double.valueOf(endLocationLat.getText());
        double endLon = Double.valueOf(endLocationLon.getText());

        int steps = Integer.valueOf(stepsTextField.getText());
        int timeInterval = Integer.valueOf(timeIntervalField.getText());

        double latDiff = startLat - endLat;
        double lonDiff = startLon - endLon;

        double latStep = latDiff / steps;
        double lonStep = lonDiff / steps;

        List<GpsPoint> points = new ArrayList<>();
        for(int i = 0; i < steps-1; i++){
            points.add(new GpsPoint(startLat - (i*latStep), startLon -(i * lonStep)));
        }
        points.add(new GpsPoint(endLat, endLon));

        GpsEmulationModel gpsEmulationModel = new GpsEmulationModel(points, timeInterval);

        return gpsEmulationModel;
    }

    @Override
    public boolean validateData() {
        errorText.setText("");
        dataValidator.startValidation();
        dataValidator.validateLat(startLocationLat.getText(),"Start Latitude");
        dataValidator.validateLon(startLocationLon.getText(), "Start Longitude");
        dataValidator.validateLat(endLocationLat.getText(), "End Latitude");
        dataValidator.validateLon(endLocationLon.getText(), "End Longitude");
        dataValidator.validateInt(stepsTextField.getText(), "Steps");
        dataValidator.validateInt(timeIntervalField.getText(), "Time Interval");
        ValidationResult validationResult = dataValidator.getResult();
        if(validationResult.isValid()) {
            return true;
        }else{
            errorText.setText("<html>"+validationResult.getErrorsText()+"</html>");
            return false;
        }
    }
}
