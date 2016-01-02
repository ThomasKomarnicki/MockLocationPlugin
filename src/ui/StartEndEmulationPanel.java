package ui;

import com.intellij.openapi.components.*;
import dataValidation.ValidationResult;
import model.GpsEmulationModel;
import model.GpsPoint;
import model.PersistableState;
import model.PersistableUiElement;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;
import presenter.PanelPresenter;
import util.CardName;
import dataValidation.DataValidator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 12/20/2015.
 */
public class StartEndEmulationPanel extends JPanel implements CardName, EmulationPanel, PersistableUiElement<StartEndEmulationPanel.State> {

    private JPanel gpsPathPanel;
    private JTextField startLocationLat;
    private JTextField startLocationLon;
//    private JButton startGPSEmulationButton;
    private JTextField stepsTextField;
    private JTextField timeIntervalField;
    private JTextField endLocationLat;
    private JTextField endLocationLon;
    private JPanel startEndRootJpanel;
    private JLabel errorText;

    private PanelPresenter panelPresenter;

    private DataValidator dataValidator;

    public StartEndEmulationPanel(){

        dataValidator = new DataValidator();
    }

    public void setPanelPresenter(PanelPresenter panelPresenter) {
        this.panelPresenter = panelPresenter;
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

        GpsEmulationModel gpsEmulationModel = new GpsEmulationModel(points, timeInterval * steps);

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

    @Nullable
    @Override
    public State getState() {
        State state = new State();
        state.startLat = startLocationLat.getText();
        state.startLon = startLocationLon.getText();
        state.endLat = endLocationLat.getText();
        state.endLon = endLocationLon.getText();
        state.steps = stepsTextField.getText();
        state.timeBetweenSteps = timeIntervalField.getText();
        return state;
    }

    @Override
    public void restoreState(State state) {
        startLocationLat.setText(state.startLat);
        startLocationLon.setText(state.startLon);
        endLocationLat.setText(state.endLat);
        endLocationLon.setText(state.endLon);

        stepsTextField.setText(state.steps);
        timeIntervalField.setText(state.timeBetweenSteps);
    }

    @Override
    public String getElementName() {
        return State.START_END_PANEL_TAG;
    }



    public static class State implements PersistableState{

        private static final String START_END_PANEL_TAG = "StartEndPanel";
        private static final String START_LAT = "startLat";
        private static final String START_LON = "startLon";
        private static final String END_LAT = "endLat";
        private static final String END_LON = "endLon";
        private static final String STEPS = "steps";
        private static final String TIME_INTERVAL = "timeInterval";
        public State(){

        }

        public String startLat;
        public String startLon;
        public String endLat;
        public String endLon;

        public String steps;
        public String timeBetweenSteps;

        @Override
        public Element save() {
            Element startEndElement = new Element(START_END_PANEL_TAG);
            startEndElement.setAttribute(START_LAT, startLat);
            startEndElement.setAttribute(START_LON, startLon);
            startEndElement.setAttribute(END_LAT, endLat);
            startEndElement.setAttribute(END_LON, endLon);
            startEndElement.setAttribute(STEPS, steps);
            startEndElement.setAttribute(TIME_INTERVAL, timeBetweenSteps);

            return startEndElement;
        }

        @Override
        public void restore(Element element) {
            startLat = element.getAttributeValue(START_LAT);
            startLon = element.getAttributeValue(START_LON);
            endLat = element.getAttributeValue(END_LAT);
            endLon = element.getAttributeValue(END_LON);
            steps = element.getAttributeValue(STEPS);
            timeBetweenSteps = element.getAttributeValue(TIME_INTERVAL);
        }
    }
}
