package com.doglandia.gpsemulator.ui;


import com.doglandia.gpsemulator.dataValidation.DataValidator;
import com.doglandia.gpsemulator.dataValidation.ValidationResult;
import com.doglandia.gpsemulator.model.GpsEmulationModel;
import com.doglandia.gpsemulator.model.GpsPoint;
import com.doglandia.gpsemulator.model.PersistableState;
import com.doglandia.gpsemulator.model.PersistableUiElement;
import com.doglandia.gpsemulator.presenter.PanelPresenter;
import com.doglandia.gpsemulator.util.CardName;
import org.jdom.Element;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 1/2/2016.
 */
public class SinglePointEmulationPanel extends JPanel implements CardName, EmulationPanel, PersistableUiElement<SinglePointEmulationPanel.State> {
    private JTextField latTextField;
    private JTextField lonTextField;
    private JLabel errorText;
    private JPanel rootPanel;

    private PanelPresenter panelPresenter;

    public SinglePointEmulationPanel(){

    }

    @Override
    public String getCardName() {
        return "singlePointEmulation";
    }

    @Override
    public GpsEmulationModel createGpsEmulationData() {
        List<GpsPoint> gpsPointList = new ArrayList<>();

        gpsPointList.add(new GpsPoint(latTextField.getText(), lonTextField.getText()));

        GpsEmulationModel gpsEmulationModel = new GpsEmulationModel(gpsPointList, 0);

        return gpsEmulationModel;
    }

    @Override
    public boolean validateData() {
        DataValidator dataValidator = new DataValidator();
        dataValidator.startValidation();
        dataValidator.validateLat(latTextField.getText(), "Lat");
        dataValidator.validateLon(lonTextField.getText(), "Lon");
        ValidationResult result = dataValidator.getResult();

        if(result.isValid()){
            errorText.setText("");
        }else{
            errorText.setText(result.getErrorsText());
        }

        return result.isValid();

    }

    @Override
    public void setPanelPresenter(PanelPresenter panelPresenter) {
        this.panelPresenter = panelPresenter;
    }

    @Override
    public State getState() {
        return new State(latTextField.getText().trim(), lonTextField.getText().trim());
    }

    @Override
    public void restoreState(State state) {
        latTextField.setText(state.latString);
        lonTextField.setText(state.lonString);
    }

    @Override
    public String getElementName() {
        return State.SINGLE_POINT_EMULATION_TAG;
    }


    public static class State implements PersistableState {

        public static final String SINGLE_POINT_EMULATION_TAG = "singlePointEmulation";

        public String latString;
        public String lonString;

        public State(){

        }

        public State(String latString, String lonString){
            this.latString = latString;
            this.lonString = lonString;
        }

        @Override
        public Element save() {
            Element element = new Element(SINGLE_POINT_EMULATION_TAG);
            element.setAttribute("lat", latString);
            element.setAttribute("lon", lonString);

            return element;
        }

        @Override
        public void restore(Element element) {
            if(element != null) {
                latString = element.getAttributeValue("lat");
                lonString = element.getAttributeValue("lon");
            }
        }
    }

    @Override
    public String toString() {
        return "Single Gps Point";
    }
}
