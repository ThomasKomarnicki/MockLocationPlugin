package ui.pointListView;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import dataValidation.DataValidator;
import model.GpsPoint;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Thomas on 12/24/2015.
 */
public class PointListView extends JPanel {
    private JTextField latTextField;
    private JTextField lonTextField;
    private JButton removeButton;
    private JPanel pointListRoot;

    public PointListView(){
        super(new GridLayoutManager(1,1));
        add(pointListRoot, new GridConstraints());
    }

    public boolean validData(DataValidator dataValidator){
        String errorString = DataValidator.validateLatString(latTextField.getText(), "");
        if(errorString == null){
            return false;
        }

        errorString = DataValidator.validateLonString(lonTextField.getText(), "");
        if(errorString == null){
            return false;
        }

        return true;
    }

    private double getLat(){
        return Double.valueOf(latTextField.getText());
    }

    private double getLon(){
        return Double.valueOf(latTextField.getText());
    }

    public GpsPoint getResolvedGpsPoint(){
        return new GpsPoint(getLat(), getLon());
    }
}
