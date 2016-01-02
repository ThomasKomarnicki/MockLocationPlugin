package ui;

import com.intellij.openapi.ui.VerticalFlowLayout;
import dataValidation.DataValidator;
import dataValidation.ValidationResult;
import model.GpsEmulationModel;
import model.GpsPoint;
import model.PersistableState;
import model.PersistableUiElement;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;
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

public class PointListEmulationPanel extends JPanel implements CardName, EmulationPanel, PersistableUiElement<PointListEmulationPanel.State> {

    private JTextField timeIntervalField;
    private JPanel pointListContent;
    private JCheckBox loopCheckBox;
    private JCheckBox reverseCheckBox;
    private JPanel pointListPanel;
    private JLabel errorText;
    private JButton addRowButton;
    private JButton deleteRowButton;

    private PanelPresenter panelPresenter;

    private List<PointListView> pointListViews = new ArrayList<>();

    private DataValidator dataValidator;


    public PointListEmulationPanel(){
        super();
        dataValidator = new DataValidator();
//        pointListViews = new ArrayList<>();
        addPointListComponents();

        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow();

                pointListPanel.revalidate();
                pointListPanel.updateUI();
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
                pointListPanel.revalidate();
                pointListPanel.updateUI();
            }
        });
    }

    private void addPointListComponents(){
        if(pointListViews.size() == 0) {
            pointListPanel.setLayout(new VerticalFlowLayout());
            for (int i = 0; i < 5; i++) {
                addRow();
            }

            revalidate();
            updateUI();
            System.out.println("added 5 items to pointListPanel");
        }
    }

    private void addRow(){
        PointListView pointListView = new PointListView(this);
        pointListPanel.add( pointListView);
        pointListView.setVisible(true);
        pointListViews.add(pointListView);
    }

    private void deleteRow(){
        int lastRow = pointListViews.size()-1;
        if(lastRow >= 1) {
            pointListPanel.remove(lastRow);
            pointListViews.remove(lastRow);
        }
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

    @Nullable
    @Override
    public State getState() {
        State state = new State();
        state.loop = loopCheckBox.isSelected();
        state.reverse = reverseCheckBox.isSelected();
        state.timeInterval = timeIntervalField.getText();

        for(int i = 0; i < pointListViews.size(); i++){
            PointListView pointListView = pointListViews.get(i);
            state.addRow(pointListView.getLatTextField().getText(), pointListView.getLonTextField().getText());
        }

        return state;
    }

    @Override
    public void restoreState(State state) {
        loopCheckBox.setSelected(state.loop);
        reverseCheckBox.setSelected(state.reverse);
        timeIntervalField.setText(state.timeInterval);

        pointListPanel.removeAll();
        pointListViews.clear();
        for(int i = 0; i < state.getRowCount(); i++){
            PointListView pointListView = new PointListView(this);
            pointListPanel.add( pointListView);
            pointListView.setVisible(true);
            pointListViews.add(pointListView);
            pointListView.setLat(state.getLatStringAt(i));
            pointListView.setLon(state.getLonStringAt(i));
        }
    }

    @Override
    public String getElementName() {
        return State.POINT_LIST_PANEL_TAG;
    }

    public void removeRow(PointListView pointListView) {
        if(pointListViews.size() == 1){
            return;
        }
        for(int i = 0; i < pointListViews.size(); i++){
            PointListView temp = pointListViews.get(i);
            if(temp == pointListView){
                pointListViews.remove(i);
                pointListPanel.remove(i);

                pointListPanel.revalidate();
                break;
            }
        }
    }


    public static class State implements PersistableState{

        private static final String POINT_LIST_PANEL_TAG = "PointListPanel";
        private static final String LOOP = "loop";
        private static final String REVERSE = "reverse";
        private static final String POINT_LIST_TAG = "pointList";
        private static final String POINT_TAG = "point";
        private static final String TIME_INTERVAL = "timeInterval";

        public List<String> latStrings;
        public List<String> lonStrings;
        public boolean loop;
        public boolean reverse;
        public String timeInterval;

        public State(){
            latStrings = new ArrayList<>();
            lonStrings = new ArrayList<>();
        }

        public void addRow(String lat, String lon){
            latStrings.add(lat);
            lonStrings.add(lon);
        }

        public int getRowCount(){
            return Math.max(latStrings.size(), lonStrings.size());
        }

        public String getLatStringAt(int index){
            if(latStrings.size() <= index){
                return "";
            }else{
                return latStrings.get(index);
            }
        }

        public String getLonStringAt(int index){
            if(lonStrings.size() <= index){
                return "";
            }else{
                return lonStrings.get(index);
            }
        }

        @Override
        public Element save() {
            Element pointListElement = new Element(POINT_LIST_PANEL_TAG);
            pointListElement.setAttribute(TIME_INTERVAL, timeInterval);
            pointListElement.setAttribute(LOOP, String.valueOf(loop));
            pointListElement.setAttribute(REVERSE, String.valueOf(reverse));

            Element pointsListElement = new Element(POINT_LIST_TAG);
            for(int i = 0; i < getRowCount(); i++){
                Element pointElement = new Element(POINT_TAG);
                pointElement.setAttribute("lat", getLatStringAt(i));
                pointElement.setAttribute("lon", getLonStringAt(i));
                pointsListElement.addContent(pointElement);
            }

            pointListElement.addContent(pointsListElement);

            return pointListElement;
        }

        @Override
        public void restore(Element element) {

            timeInterval = element.getAttributeValue(TIME_INTERVAL);
            loop = Boolean.valueOf(element.getAttributeValue(LOOP));
            reverse = Boolean.valueOf(element.getAttributeValue(REVERSE));

            Element pointsListElement = element.getChild(POINT_LIST_TAG);
            List<Element> children = pointsListElement.getChildren();
            for(Element child : children){
                addRow(child.getAttributeValue("lat"), child.getAttributeValue("lon"));
            }
        }
    }

}
