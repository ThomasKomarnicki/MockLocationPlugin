package ui.pointListView;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

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

    public boolean validData(){
        // TODO
        return true;
    }

    public double getLat(){
        return Double.valueOf(latTextField.getText());
    }

    public double getLon(){
        return Double.valueOf(latTextField.getText());
    }
}
