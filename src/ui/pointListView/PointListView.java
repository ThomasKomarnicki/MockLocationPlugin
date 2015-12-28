package ui.pointListView;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import dataValidation.DataValidator;
import model.GpsPoint;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Created by Thomas on 12/24/2015.
 */
public class PointListView extends JPanel {

    private static final Color errorColor = new Color(255,0,0);
    private static final Color standardColor = Color.BLACK;

    private JTextField latTextField;
    private JTextField lonTextField;
    private JButton removeButton;
    private JPanel pointListRoot;

    public PointListView(){
        super(new GridLayoutManager(1,1));
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
}
