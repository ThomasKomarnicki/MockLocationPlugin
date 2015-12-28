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

    private PanelPresenter panelPresenter;

    private List<PointListView> pointListViews;

    private DataValidator dataValidator;


    public PointListEmulationPanel(){
        super();
        dataValidator = new DataValidator();
        pointListViews = new ArrayList<>();
        addPointListComponents();
    }

    private void addPointListComponents(){
        pointListPanel.setLayout(new VerticalFlowLayout());
        for(int i = 0; i < 5; i++){

            PointListView pointListView = new PointListView();
            pointListPanel.add( pointListView);
            pointListView.setVisible(true);
            pointListViews.add(pointListView);
        }

        revalidate();
        updateUI();
        System.out.println("added 5 items to pointListPanel");
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
        return new GpsEmulationModel(gpsPoints, Integer.valueOf(timeIntervalField.getText()));
    }

    @Override
    public boolean validateData() {
//        boolean valid = true;
        dataValidator.startValidation();
        dataValidator.validateInt(timeIntervalField.getText(), "Time Interval");
        for(PointListView pointListView : pointListViews){
            if(!pointListView.validateData(dataValidator)){
//                valid = false;
            }
        }

        ValidationResult validationResult = dataValidator.getResult();
        if(!validationResult.isValid()) {
            errorText.setText(validationResult.getErrorsText());
            return false;
        }else {
            errorText.setText("");
            return true;
        }
    }

    public void setPanelPresenter(PanelPresenter panelPresenter) {
        this.panelPresenter = panelPresenter;
    }
}
