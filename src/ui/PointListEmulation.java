package ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import model.GpsEmulationModel;
import presenter.Presenter;
import service.EmulationService;
import util.CardName;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 12/20/2015.
 */
public class PointListEmulation extends JPanel implements CardName, EmulationPanel {

    private JTextField startLocationLat;
    private JTextField startLocationLon;
    private JButton startGPSEmulationButton;
    private JTextField timeIntervalField;
    private JPanel pointListContent;
    private JCheckBox loopCheckBox;
    private JCheckBox reverseCheckBox;
    private JLabel errorText;

    private Presenter presenter;


    public PointListEmulation(){

        startGPSEmulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                presenter.onEmulationButtonClick();

                errorText.setText("");

            }
        });

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
    public void onGpsEmulationStopped() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                startGPSEmulationButton.setText("Start GPS Emulation");
            }
        });
    }

    @Override
    public void onGpsEmulationStarted() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                startGPSEmulationButton.setText("Stop GPS Emulation");
            }
        });
    }

    @Override
    public GpsEmulationModel createGpsEmulationData() {
        return null;
    }

    @Override
    public boolean validateData() {
        return false;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
