package ui;

import com.intellij.openapi.ui.VerticalFlowLayout;
import model.GpsEmulationModel;
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

    private PanelPresenter panelPresenter;

    private List<PointListView> pointListViews = new ArrayList<>();


    public PointListEmulationPanel(){
        super();
        addPointListComponents();
    }

    private void addPointListComponents(){
//        setLayout(new VerticalFlowLayout());
        pointListPanel.setLayout(new VerticalFlowLayout());
        for(int i = 0; i < 5; i++){

            PointListView pointListView = new PointListView();
//            pointListView.setPreferredSize(new Dimension(-1,50));
            pointListPanel.add( pointListView);
//            pointListPanel.add( new JButton("Please "+i));
            pointListView.setVisible(true);
            pointListViews.add(pointListView);
        }

        pointListPanel.revalidate();
        pointListPanel.repaint();
        pointListPanel.updateUI();
        revalidate();
        updateUI();
        System.out.println("added 5 items to pointListPanel");
//        pointListPanel.invalidate();
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
        return null;
    }

    @Override
    public boolean validateData() {
        return true;
    }

    public void setPanelPresenter(PanelPresenter panelPresenter) {
        this.panelPresenter = panelPresenter;
    }
}
