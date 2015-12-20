package ui;

import com.google.common.eventbus.Subscribe;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.uiDesigner.core.GridLayoutManager;
import model.event.EmulationStartedEvent;
import model.event.EmulationStoppedEvent;
import org.jetbrains.annotations.NotNull;
import service.EmulationService;
import service.ProgressCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
    private JPanel blankJpanel;
    private JPanel emulationContentPanel;

    public MainToolWindow(){

        blankJpanel = new JPanel(new GridLayoutManager(1,1)){
            @Override
            public String toString() {
                return "Blank Panel";
            }
        };

        mockingToolsComboBox.addItem(startEndEmulationPanel);
        mockingToolsComboBox.addItem(blankJpanel);

        mockingToolsComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    emulationContentPanel.removeAll();
                    mockingToolsComboBox.hidePopup();
                    System.out.println("selected "+e.getItem());
                    emulationContentPanel.add((Component) e.getItem());

                }

            }
        });

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
