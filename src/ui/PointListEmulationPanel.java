package ui;

import com.intellij.openapi.ui.VerticalFlowLayout;
import dataValidation.DataValidator;
import dataValidation.ValidationResult;
import model.GpsEmulationModel;
import model.GpsPoint;
import presenter.PanelPresenter;
import ui.pointListView.PointListView;
import util.CardName;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 12/20/2015.
 */
public class PointListEmulationPanel extends JPanel implements CardName, EmulationPanel {

    private JTextField timeIntervalField;
    private JPanel pointListContent;
    private JCheckBox loopCheckBox;
    private JCheckBox reverseCheckBox;
    private JPanel pointListPanel;
    private JLabel errorText;
    private JButton addRowButton;
    private JButton deleteRowButton;

    private PanelPresenter panelPresenter;

    private List<PointListView> pointListViews;

    private DataValidator dataValidator;


    public PointListEmulationPanel(){
        super();
        dataValidator = new DataValidator();
        pointListViews = new ArrayList<>();
        addPointListComponents();

        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow();

                revalidate();
                updateUI();
            }
        });
        deleteRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRow();

                revalidate();
                updateUI();
            }
        });
    }

    private void addPointListComponents(){
        pointListPanel.setLayout(new VerticalFlowLayout());
        for(int i = 0; i < 5; i++){
            addRow();
        }

        revalidate();
        updateUI();
        System.out.println("added 5 items to pointListPanel");
    }

    private void addRow(){
        PointListView pointListView = new PointListView();
        pointListPanel.add( pointListView);
        pointListView.setVisible(true);
        pointListViews.add(pointListView);
    }

    private void deleteRow(){
        int lastRow = pointListViews.size()-1;
        pointListPanel.remove(lastRow);
        pointListViews.remove(lastRow);
    }

    @Override
    public String toString() {
        return "Point List Emulation";
    }

    @Override
    public String getCardName() {
        return "pointListEmulation";
    }

    @Override
    public GpsEmulationModel createGpsEmulationData() {
        List<GpsPoint> gpsPoints = new ArrayList<>();
        for(PointListView pointListView : pointListViews){
            gpsPoints.add(pointListView.getResolvedGpsPoint());
        }
        if(reverseCheckBox.isSelected()){
            gpsPoints = getReversedList(gpsPoints);
        }
        return new GpsEmulationModel(gpsPoints, Integer.valueOf(timeIntervalField.getText())*gpsPoints.size(),loopCheckBox.isSelected());
    }

    @Override
    public boolean validateData() {
        boolean valid = true;
        dataValidator.startValidation();
        dataValidator.validateInt(timeIntervalField.getText(), "Time Interval");

        for(PointListView pointListView : pointListViews){
            if(!pointListView.validateData()){
                valid = false;
            }
        }

        ValidationResult validationResult = dataValidator.getResult();
        if(!valid || !validationResult.isValid()) {
            String errorTextString = "";
            if(!valid) {
                errorTextString = "There are errors in the location fields, All points must be valid doubles";
            }
            if(!validationResult.isValid()){
                errorTextString += validationResult.getErrorsText();
            }
            errorText.setText("<html>"+errorTextString+"</html>");
            return false;
        }else {
            errorText.setText("");
            return true;
        }
    }

    public void setPanelPresenter(PanelPresenter panelPresenter) {
        this.panelPresenter = panelPresenter;
    }

    private static List<GpsPoint> getReversedList(List<GpsPoint> points){
        List<GpsPoint> reversed = new ArrayList<>();
        for(int i = points.size()-1; i >=0; i--){
            reversed.add(points.get(i));
        }
        return reversed;
    }
}
