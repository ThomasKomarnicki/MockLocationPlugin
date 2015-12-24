package ui;

import com.intellij.openapi.application.ApplicationManager;
import model.GpsEmulationModel;
import presenter.PanelPresenter;
import util.CardName;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 12/20/2015.
 */
public class PointListEmulation extends JPanel implements CardName, EmulationPanel {

    private JTextField timeIntervalField;
    private JPanel pointListContent;
    private JCheckBox loopCheckBox;
    private JCheckBox reverseCheckBox;

    private PanelPresenter panelPresenter;


    public PointListEmulation(){

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
        return false;
    }

    public void setPanelPresenter(PanelPresenter panelPresenter) {
        this.panelPresenter = panelPresenter;
    }
}
