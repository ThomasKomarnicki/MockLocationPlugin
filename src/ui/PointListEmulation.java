package ui;

import util.CardName;

import javax.swing.*;

/**
 * Created by Thomas on 12/20/2015.
 */
public class PointListEmulation extends JPanel implements CardName {

    private JTextField startLocationLat;
    private JTextField startLocationLon;
    private JButton startGPSEmulationButton;
    private JTextField timeIntervalField;
    private JPanel pointListContent;


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
}
