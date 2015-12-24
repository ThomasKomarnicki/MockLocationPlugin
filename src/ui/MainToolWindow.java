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
import presenter.PanelPresenter;
import service.ProgressCallback;
import util.CardName;

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

    private StartEndEmulationPanel startEndEmulationPanel;
    private PointListEmulation pointListEmulation;

    private JPanel emulationContentPanel;
    private JButton emulationActionButton;
    private CardLayout cardLayout;

    private PanelPresenter panelPresenter;

    public MainToolWindow(){

        cardLayout = (CardLayout)(emulationContentPanel.getLayout());

        panelPresenter = new PanelPresenter(this);

        mockingToolsComboBox.addItem(startEndEmulationPanel);
        mockingToolsComboBox.addItem(pointListEmulation);


        startEndEmulationPanel.setPanelPresenter(panelPresenter);
        panelPresenter.setCurrentEmulationPanel(startEndEmulationPanel);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);

        mockingToolsComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    EmulationPanel emulationPanel = (EmulationPanel) e.getItem();
                    mockingToolsComboBox.hidePopup();
//                    System.out.println("selected "+e.getItem());
                    panelPresenter.setCurrentEmulationPanel(emulationPanel);
                    emulationPanel.setPanelPresenter(panelPresenter);
                    cardLayout.show(emulationContentPanel, ((CardName)e.getItem()).getCardName());
                }

            }
        });

        emulationActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPresenter.onEmulationButtonClick();
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
                emulationActionButton.setText("Start GPS Emulation");
                progressBar.setValue(0);
            }
        });

    }

    @Subscribe
    public void onGpsEmulationStarted(EmulationStartedEvent emulationStartedEvent){
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                emulationActionButton.setText("Stop GPS Emulation");
            }
        });

    }

}
