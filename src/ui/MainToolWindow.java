package ui;

import com.google.common.eventbus.Subscribe;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import model.event.EmulationStartedEvent;
import model.event.EmulationStoppedEvent;
import org.jetbrains.annotations.NotNull;
import service.EmulationService;
import service.ProgressCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 12/18/2015.
 */
public class MainToolWindow implements ToolWindowFactory, ProgressCallback {

    private Project project;
    private ToolWindow mainToolWindow;
    private JPanel toolWindowContent;
    private JComboBox mockingToolsComboBox;
    private JProgressBar progressBar;

    private StartEndEmulation startEndEmulationPanel;

    public MainToolWindow(){

        startEndEmulationPanel.setProgressCallback(this);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);

        mockingToolsComboBox.addItem(startEndEmulationPanel);
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

    @Subscribe
    public void onGpsEmulationStopped(EmulationStoppedEvent emulationStoppedEvent){
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setValue(0);
            }
        });

    }

    @Subscribe
    public void onGpsEmulationStarted(EmulationStartedEvent emulationStartedEvent){
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

}
