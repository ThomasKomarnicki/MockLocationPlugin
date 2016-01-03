package com.doglandia.gpsemulator;

import com.doglandia.gpsemulator.model.PersistableState;
import com.doglandia.gpsemulator.presenter.ToolWindowPresenterInterface;
import com.doglandia.gpsemulator.service.SettingsService;
import com.doglandia.gpsemulator.ui.MainToolWindow;
import com.doglandia.gpsemulator.ui.PointListEmulationPanel;
import com.doglandia.gpsemulator.ui.SinglePointEmulationPanel;
import com.doglandia.gpsemulator.ui.StartEndEmulationPanel;
import com.doglandia.gpsemulator.ui.pointListView.PointListView;
import org.jdom.Element;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.doglandia.gpsemulator.TestUtils.*;
import static org.junit.Assert.*;

/**
 * Created by Thomas on 1/2/2016.
 */
public class PersistenceTest {

    private static class TestToolWindowPresenter implements ToolWindowPresenterInterface {

        public MainToolWindow mainToolWindow;
        public PointListEmulationPanel pointListPanel;
        public StartEndEmulationPanel startEndPanel;
        public SinglePointEmulationPanel singlePointPanel;

        public MainToolWindow.State toolWindowState;
        public StartEndEmulationPanel.State startEndState;
        public PointListEmulationPanel.State pointListState;
        public SinglePointEmulationPanel.State singlePointState;

        public TestToolWindowPresenter() {
            mainToolWindow = new MainToolWindow("test");
            pointListPanel = new PointListEmulationPanel("test");
            startEndPanel = new StartEndEmulationPanel();
            singlePointPanel = new SinglePointEmulationPanel();
        }

        @Override
        public List<PersistableState> getPersistableStates() {
            List<PersistableState> states = new ArrayList<>();
            states.add(mainToolWindow.getState());
            states.add(startEndPanel.getState());
            states.add(singlePointPanel.getState());
            states.add(pointListPanel.getState());

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
            singlePointState.restore(rootElement.getChild(mainToolWindow.getSinglePointEmulationPanel().getElementName()));
        }
    }

    @Test
    public void testUiPersistence(){
        SettingsService settingsService = new SettingsService();
        TestToolWindowPresenter toolWindowPresenter = new TestToolWindowPresenter();
        settingsService.setToolWindowPresenter(toolWindowPresenter);

        // add values to the fields using reflection,
        // test to make sure the corresponding states come out with these values on the other side
        setTextOfField(toolWindowPresenter.mainToolWindow, "portTextField","1234");

        setTextOfField(toolWindowPresenter.startEndPanel, "startLocationLat","1");
        setTextOfField(toolWindowPresenter.startEndPanel, "startLocationLon","2");
        setTextOfField(toolWindowPresenter.startEndPanel, "endLocationLat","3");
        setTextOfField(toolWindowPresenter.startEndPanel, "endLocationLon","4");
        setTextOfField(toolWindowPresenter.startEndPanel, "stepsTextField","5");
        setTextOfField(toolWindowPresenter.startEndPanel, "timeIntervalField","6");

        setTextOfField(toolWindowPresenter.singlePointPanel, "latTextField","1");
        setTextOfField(toolWindowPresenter.singlePointPanel, "lonTextField","2");


        setTextOfField(toolWindowPresenter.pointListPanel, "timeIntervalField","5");
        setFieldSelection(toolWindowPresenter.pointListPanel, "loopCheckBox", true);
        setFieldSelection(toolWindowPresenter.pointListPanel, "reverseCheckBox", true);
        List<PointListView> pointListViews = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            PointListView pointListView = new PointListView(toolWindowPresenter.pointListPanel);
            pointListView.setLat(""+i);
            pointListView.setLon(""+i);
            pointListViews.add(pointListView);
        }
        setFieldValue(toolWindowPresenter.pointListPanel, "pointListViews", pointListViews);

        // do save / load conversion
        Element savedSettings = settingsService.getState();
        settingsService.loadState(savedSettings);

        assertEquals(toolWindowPresenter.toolWindowState.port, "1234");

        assertEquals(toolWindowPresenter.startEndState.startLat, "1");
        assertEquals(toolWindowPresenter.startEndState.startLon, "2");
        assertEquals(toolWindowPresenter.startEndState.endLat, "3");
        assertEquals(toolWindowPresenter.startEndState.endLon, "4");
        assertEquals(toolWindowPresenter.startEndState.steps, "5");
        assertEquals(toolWindowPresenter.startEndState.timeBetweenSteps, "6");

        assertEquals(toolWindowPresenter.singlePointState.latString, "1");
        assertEquals(toolWindowPresenter.singlePointState.lonString, "2");

        assertEquals(toolWindowPresenter.pointListState.timeInterval, "5");
        assertEquals(toolWindowPresenter.pointListState.loop, true);
        assertEquals(toolWindowPresenter.pointListState.reverse, true);

        for(int i = 0; i < toolWindowPresenter.pointListState.getRowCount(); i++){
            assertEquals(toolWindowPresenter.pointListState.getLatStringAt(i),""+i);
            assertEquals(toolWindowPresenter.pointListState.getLonStringAt(i),""+i);
        }


    }
}