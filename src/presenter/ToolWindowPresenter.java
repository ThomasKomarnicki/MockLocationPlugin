package presenter;

import com.intellij.openapi.components.ServiceManager;
import model.PersistableState;
import service.SettingsService;
import ui.MainToolWindow;

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
}
