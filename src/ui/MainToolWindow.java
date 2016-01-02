package ui;

import com.google.common.eventbus.Subscribe;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import model.PersistableState;
import model.PersistableUiElement;
import model.event.EmulationStartedEvent;
import model.event.EmulationStoppedEvent;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import presenter.PanelPresenter;
import presenter.ToolWindowPresenter;
import service.EmulationService;
import service.ProgressCallback;
import service.SettingsService;
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
public class MainToolWindow implements ToolWindowFactory, ProgressCallback, PersistableUiElement<MainToolWindow.State> {

    private ToolWindow mainToolWindow;
    private JPanel toolWindowContent;
    private JComboBox mockingToolsComboBox;
    private JProgressBar progressBar;

    private StartEndEmulationPanel startEndEmulationPanel;
    private PointListEmulationPanel pointListEmulationPanel;

    private JPanel emulationContentPanel;
    private JButton emulationActionButton;
    private JTextField portTextField;
    private CardLayout cardLayout;

    private PanelPresenter panelPresenter;

    private ToolWindowPresenter toolWindowPresenter;

    public MainToolWindow(){

        toolWindowPresenter = new ToolWindowPresenter(this);

        cardLayout = (CardLayout)(emulationContentPanel.getLayout());

        panelPresenter = new PanelPresenter(this);

        mockingToolsComboBox.addItem(startEndEmulationPanel);
        mockingToolsComboBox.addItem(pointListEmulationPanel);


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
        emulationContentPanel.revalidate();
        emulationContentPanel.updateUI();

    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
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

    public int getPort(){

        String portText = portTextField.getText();

        try {
            int port = Integer.valueOf(portText);
            return port;
        }catch (Exception e){
            e.printStackTrace();
        }

        return -1;
    }

    public StartEndEmulationPanel getStartEndEmulationPanel() {
        return startEndEmulationPanel;
    }

    public PointListEmulationPanel getPointListEmulationPanel() {
        return pointListEmulationPanel;
    }

    @Override
    public State getState(){
        return new State(this);
    }

    @Override
    public void restoreState(State state) {
        portTextField.setText(state.port);
        int selectedPanel = state.selectedPanel;
    }

    @Override
    public String getElementName() {
        return State.TOOL_WINDOW_TAG;
    }


    public static class State implements PersistableState{
        private static final String TOOL_WINDOW_TAG = "MainToolWindow";
        private static final String PORT = "port";
        private static final String SELECTED_PANEL = "panel";

        public String port;
        public int selectedPanel;

        public State(){

        }

        public State(MainToolWindow mainToolWindow){
            port = mainToolWindow.portTextField.getText();
            selectedPanel = 0; // todo
        }

        @Override
        public Element save() {
            final Element toolWindowElement = new Element(TOOL_WINDOW_TAG);
            toolWindowElement.setAttribute(PORT, port);
            toolWindowElement.setAttribute(SELECTED_PANEL, String.valueOf(selectedPanel));

            return toolWindowElement;
        }

        @Override
        public void restore(Element element) {
            port = element.getAttributeValue(PORT);
            selectedPanel = Integer.valueOf(element.getAttributeValue(SELECTED_PANEL));
        }
    }

}
