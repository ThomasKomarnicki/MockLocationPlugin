package ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import service.EmulationService;
import service.ProgressCallback;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 12/18/2015.
 */
public class MainToolWindow implements ToolWindowFactory, ProgressCallback {

    private Project project;
    private ToolWindow mainToolWindow;
    private JPanel toolWindowContent;
//    private JTextField startLocationLat;
//    private JTextField startLocationLon;
    private JComboBox mockingToolsComboBox;
//    private JTextField stepsTextField;
//    private JTextField timeIntervalField;
//    private JTextField endLocationLat;
//    private JTextField endLocationLon;
//    private JButton startGPSEmulationButton;
    private JProgressBar progressBar;
    private StartEndEmulation startEndEmulationPanel;

    public MainToolWindow(){

        startEndEmulationPanel.setProgressCallback(this);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.project = project;
        this.mainToolWindow = toolWindow;
        ContentFactory factory = ContentFactory.SERVICE.getInstance();
        Content content = factory.createContent(this.toolWindowContent, "", false);
        this.mainToolWindow.getContentManager().addContent(content);
    }

    @Override
    public void onProgressEvent(final int progress) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setValue(progress);
            }
        });

    }

}
