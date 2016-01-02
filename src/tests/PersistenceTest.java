package tests;

import model.PersistableState;
import org.jdom.Element;
import org.junit.Test;
import presenter.ToolWindowPresenter;
import service.SettingsService;
import ui.MainToolWindow;
import ui.PointListEmulationPanel;
import ui.SinglePointEmulationPanel;
import ui.StartEndEmulationPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 1/2/2016.
 */
public class PersistenceTest {

    private static class TestToolWindowPresenter extends ToolWindowPresenter{

        private MainToolWindow mainToolWindow;
        private PointListEmulationPanel pointListPanel;
        private StartEndEmulationPanel startEndPanel;
        private SinglePointEmulationPanel singlePointPanel;

        private MainToolWindow.State toolWindowState;
        StartEndEmulationPanel.State startEndState;
        PointListEmulationPanel.State pointListState;
        SinglePointEmulationPanel.State singlePointState;

        public TestToolWindowPresenter() {
            super(null);
            mainToolWindow = new MainToolWindow();
            pointListPanel = new PointListEmulationPanel();
            startEndPanel = new StartEndEmulationPanel();
            singlePointPanel = new SinglePointEmulationPanel();
        }

        @Override
        public List<PersistableState> getPersistableStates() {
            List<PersistableState> states = new ArrayList<>();
            states.add(mainToolWindow.getState());

            return states;
        }

        @Override
        public void loadPersistableStates(Element rootElement) {
            toolWindowState = new MainToolWindow.State();
            toolWindowState.restore(rootElement.getChild(mainToolWindow.getElementName()));

            startEndState = new StartEndEmulationPanel.State();
            startEndState.restore(rootElement.getChild(mainToolWindow.getStartEndEmulationPanel().getElementName()));

            pointListState = new PointListEmulationPanel.State();
            pointListState.restore(rootElement.getChild(mainToolWindow.getPointListEmulationPanel().getElementName()));

            singlePointState = new SinglePointEmulationPanel.State();
            singlePointState.restore(rootElement.getChild(mainToolWindow.getPointListEmulationPanel().getElementName()));
        }
    }

    @Test
    public void testUiPersistence(){
        SettingsService settingsService = new SettingsService();
        ToolWindowPresenter toolWindowPresenter = new TestToolWindowPresenter();
        MainToolWindow mainToolWindow = new MainToolWindow();
        settingsService.setToolWindowPresenter(toolWindowPresenter);

    }
}