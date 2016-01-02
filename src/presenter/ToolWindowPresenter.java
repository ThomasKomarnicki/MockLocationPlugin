package presenter;

import com.intellij.openapi.components.ServiceManager;
import model.PersistableState;
import org.jdom.Element;
import service.SettingsService;
import ui.MainToolWindow;
import ui.PointListEmulationPanel;
import ui.StartEndEmulationPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 1/1/2016.
 */
public class ToolWindowPresenter {

    private MainToolWindow mainToolWindow;

    private SettingsService settingsService;

    public ToolWindowPresenter(MainToolWindow mainToolWindow) {
        this.mainToolWindow = mainToolWindow;

        settingsService = ServiceManager.getService(SettingsService.class);
        settingsService.setToolWindowPresenter(this);

    }


    public List<PersistableState> getPersistableStates() {
        List<PersistableState> persistableStates = new ArrayList<>();
        persistableStates.add(mainToolWindow.getState());
        persistableStates.add(mainToolWindow.getStartEndEmulationPanel().getState());
        persistableStates.add(mainToolWindow.getPointListEmulationPanel().getState());

        return persistableStates;
    }

    public void loadPersistableStates(Element rootElement){
        MainToolWindow.State state = new MainToolWindow.State();
        state.restore(rootElement.getChild(mainToolWindow.getElementName()));
        mainToolWindow.restoreState(state);

        StartEndEmulationPanel.State state1 = new StartEndEmulationPanel.State();
        state1.restore(rootElement.getChild(mainToolWindow.getStartEndEmulationPanel().getElementName()));
        mainToolWindow.getStartEndEmulationPanel().restoreState(state1);

        PointListEmulationPanel.State state2 = new PointListEmulationPanel.State();
        state2.restore(rootElement.getChild(mainToolWindow.getPointListEmulationPanel().getElementName()));
        mainToolWindow.getPointListEmulationPanel().restoreState(state2);
    }
}
