package com.doglandia.gpsemulator.ui.pointListView;

import com.doglandia.gpsemulator.dataValidation.DataValidator;
import com.doglandia.gpsemulator.model.GpsPoint;
import com.doglandia.gpsemulator.ui.PointListEmulationPanel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 12/24/2015.
 */
public class PointListView extends JPanel {

    private static final Color errorColor = new Color(255,0,0);
    private static final Color standardColor = Color.BLACK;

    private JTextField latTextField;
    private JTextField lonTextField;
    private JPanel pointListRoot;
    private JButton deleteRowButton;

    private PointListEmulationPanel pointListEmulationPanel;

    public PointListView(final PointListEmulationPanel pointListEmulationPanel){
        super(new GridLayoutManager(1,1));
        this.pointListEmulationPanel = pointListEmulationPanel;
        add(pointListRoot, new GridConstraints());

        latTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                latTextField.setForeground(standardColor);
            }
        });

        lonTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lonTextField.setForeground(standardColor);
            }
        });

        deleteRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pointListEmulationPanel.removeRow(PointListView.this);
            }
        });

    }

    public boolean validateData(){

//        boolean currentlyValid = dataValidator.isCurrentlyValid();
        String errorString = DataValidator.validateLatString(latTextField.getText(), "");
        if(errorString != null){
            latTextField.setForeground(errorColor);
            return false;
        }

//        currentlyValid = dataValidator.isCurrentlyValid();
        errorString = DataValidator.validateLonString(lonTextField.getText(), "");
        if(errorString != null){
            lonTextField.setForeground(errorColor);
            return false;
        }



        return true;
    }

    private double getLat(){
        return Double.valueOf(latTextField.getText());
    }

    private double getLon(){
        return Double.valueOf(lonTextField.getText());
    }

    public GpsPoint getResolvedGpsPoint(){
        return new GpsPoint(getLat(), getLon());
    }

    public JTextField getLatTextField() {
        return latTextField;
    }

    public JTextField getLonTextField() {
        return lonTextField;
    }

    public void setLat(String latString) {
        latTextField.setText(latString);
    }

    public void setLon(String lonString) {
        lonTextField.setText(lonString);
    }
}
