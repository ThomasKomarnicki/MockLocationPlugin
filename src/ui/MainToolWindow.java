package ui;

import com.google.common.eventbus.Subscribe;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import model.event.EmulationStartedEvent;
import model.event.EmulationStoppedEvent;
import org.jetbrains.annotations.NotNull;
import service.ProgressCallback;
import util.CardName;

import javax.swing.*;
import java.awt.*;
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

    private StartEndEmulationPanel startEndEmulationPanel;
    private PointListEmulation pointListEmulation;

    private JPanel emulationContentPanel;
    private CardLayout cardLayout;

    public MainToolWindow(){

        cardLayout = (CardLayout)(emulationContentPanel.getLayout());


        mockingToolsComboBox.addItem(startEndEmulationPanel);
        mockingToolsComboBox.addItem(pointListEmulation);

        mockingToolsComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    mockingToolsComboBox.hidePopup();
                    System.out.println("selected "+e.getItem());
                    cardLayout.show(emulationContentPanel, ((CardName)e.getItem()).getCardName());
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
