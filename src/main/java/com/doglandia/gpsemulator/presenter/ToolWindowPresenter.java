package com.doglandia.gpsemulator.presenter;

import com.doglandia.gpsemulator.model.PersistableState;
import com.doglandia.gpsemulator.service.SettingsService;
import com.doglandia.gpsemulator.ui.MainToolWindow;
import com.doglandia.gpsemulator.ui.PointListEmulationPanel;
import com.doglandia.gpsemulator.ui.SinglePointEmulationPanel;
import com.doglandia.gpsemulator.ui.StartEndEmulationPanel;
import com.intellij.openapi.components.ServiceManager;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 1/1/2016.
 */
public class ToolWindowPresenter implements ToolWindowPresenterInterface {

    private MainToolWindow mainToolWindow;

    private SettingsService settingsService;

    public ToolWindowPresenter(){

    }

    public ToolWindowPresenter(MainToolWindow mainToolWindow) {
        this.mainToolWindow = mainToolWindow;

        settingsService = ServiceManager.getService(SettingsService.class);
        settingsService.setToolWindowPresenter(this);

    }

    @Override
    public List<PersistableState> getPersistableStates() {
        List<PersistableState> persistableStates = new ArrayList<>();
        persistableStates.add(mainToolWindow.getState());
        persistableStates.add(mainToolWindow.getStartEndEmulationPanel().getState());
        persistableStates.add(mainToolWindow.getPointListEmulationPanel().getState());

        return persistableStates;
    }

    @Override
    public void loadPersistableStates(Element rootElement){
        MainToolWindow.State state = new MainToolWindow.State();
        state.restore(rootElement.getChild(mainToolWindow.getElementName()));
        if(state != null) {
            mainToolWindow.restoreState(state);
        }

        StartEndEmulationPanel.State state1 = new StartEndEmulationPanel.State();
        state1.restore(rootElement.getChild(mainToolWindow.getStartEndEmulationPanel().getElementName()));
        mainToolWindow.getStartEndEmulationPanel().restoreState(state1);

        PointListEmulationPanel.State state2 = new PointListEmulationPanel.State();
        state2.restore(rootElement.getChild(mainToolWindow.getPointListEmulationPanel().getElementName()));
        mainToolWindow.getPointListEmulationPanel().restoreState(state2);

        SinglePointEmulationPanel.State state3 = new SinglePointEmulationPanel.State();
        state3.restore(rootElement.getChild(mainToolWindow.getSinglePointEmulationPanel().getElementName()));
        mainToolWindow.getSinglePointEmulationPanel().restoreState(state3);
    }
}
