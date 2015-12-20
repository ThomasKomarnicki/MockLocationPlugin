package ui;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import service.EmulationService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 12/18/2015.
 */
public class MainToolWindow implements ToolWindowFactory {

    private Project project;
    private ToolWindow mainToolWindow;
    private JPanel toolWindowContent;
    private JPanel gpsPathPanel;
    private JTextField startLocationLat;
    private JTextField startLocationLon;
    private JComboBox actionComboBox;
    private JTextField stepsTextField;
    private JTextField timeIntervalField;
    private JTextField endLocationLat;
    private JTextField endLocationLon;
    private JButton startGPSEmulationButton;

    public MainToolWindow(){
        startGPSEmulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    double startLat = Double.valueOf(startLocationLat.getText());
                    double startLon = Double.valueOf(startLocationLon.getText());

                    double endLat = Double.valueOf(endLocationLat.getText());
                    double endLon = Double.valueOf(endLocationLon.getText());

                    int steps = Integer.valueOf(stepsTextField.getText());
                    int timeInterval = Integer.valueOf(timeIntervalField.getText());

                    EmulationService emulationService = ServiceManager.getService(EmulationService.class);
                    emulationService.emulatePath(startLat, startLon, endLat, endLon, steps, steps * timeInterval * 1000);
                } catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.project = project;
        this.mainToolWindow = toolWindow;
        ContentFactory factory = ContentFactory.SERVICE.getInstance();
        Content content = factory.createContent(this.toolWindowContent, "", false);
        this.mainToolWindow.getContentManager().addContent(content);
    }

}
