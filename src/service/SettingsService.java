package service;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.diagnostic.Logger;
import model.PersistableState;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;
import presenter.ToolWindowPresenter;
import ui.MainToolWindow;
import ui.PointListEmulationPanel;
import ui.StartEndEmulationPanel;

import java.util.List;

/**
 * Created by Thomas on 12/30/2015.
 */
@State(
        name = "PluginSettings",
        storages = {
                @Storage(
                        file = StoragePathMacros.APP_CONFIG + "/plugin_settings.xml"
                )}
)
public class SettingsService implements PersistentStateComponent<Element>{

    private static final String SETTINGS_TAG = "PluginSettings";

    private static final String TOOL_WINDOW_TAG = "MainToolWindow";
//    private static final String PORT = "port";
//    private static final String SELECTED_PANEL = "panel";

    private static final String START_END_PANEL_TAG = "StartEndPanel";
    private static final String START_LAT = "startLat";
    private static final String START_LON = "startLon";
    private static final String END_LAT = "endLat";
    private static final String END_LON = "endLon";
    private static final String STEPS = "steps";
    private static final String TIME_INTERVAL = "timeInterval";

    private static final String POINT_LIST_PANEL_TAG = "PointListPanel";
    private static final String LOOP = "loop";
    private static final String REVERSE = "reverse";
    private static final String POINT_LIST_TAG = "pointList";
    private static final String POINT_TAG = "point";

    private static final String SINGLE_POINT_PANEL_TAG = "SinglePointPanel";

    private PointListEmulationPanel.State pointListState;
    private StartEndEmulationPanel.State startEndState;
    private MainToolWindow.State toolWindowState;

    private final  Logger LOG = Logger.getInstance(SettingsService.class);
    private ToolWindowPresenter toolWindowPresenter;

//    public void setPointListState(PointListEmulationPanel.State pointListState) {
//        this.pointListState = pointListState;
//    }
//
//    public void setStartEndState(StartEndEmulationPanel.State startEndState) {
//        this.startEndState = startEndState;
//    }
//
//    public void setToolWindowState(MainToolWindow.State toolWindowState) {
//        this.toolWindowState = toolWindowState;
//    }
//
//    public PointListEmulationPanel.State getPointListState() {
//        return pointListState;
//    }
//
//    public StartEndEmulationPanel.State getStartEndState() {
//        return startEndState;
//    }
//
//    public MainToolWindow.State getToolWindowState() {
//        return toolWindowState;
//    }

    /**
     * constructs xml tree representing the state of the tool window and its components
     * @return
     */
    @Nullable
    @Override
    public Element getState() {
        System.out.println("saving settings state");
        LOG.debug("saving settings state");
        final Element element = new Element(SETTINGS_TAG);

        List<PersistableState> states = toolWindowPresenter.getPersistableStates();
        for(PersistableState state : states){
            element.addContent(state.save());
        }
//        element.addContent(toolWindowState.save());
//        element.addContent(startEndState.save());
//        element.addContent(pointListState.save());

        // add tool window data
//        final Element toolWindowElement = new Element(TOOL_WINDOW_TAG);
//        element.setAttribute(PORT,toolWindowState.port);
//        element.setAttribute(SELECTED_PANEL, String.valueOf(toolWindowState.selectedPanel));
//        element.addContent(toolWindowElement);

        // add start end panel data
//        Element startEndElement = new Element(START_END_PANEL_TAG);
//        startEndElement.setAttribute(START_LAT, startEndState.startLat);
//        startEndElement.setAttribute(START_LON, startEndState.startLon);
//        startEndElement.setAttribute(END_LAT, startEndState.endLat);
//        startEndElement.setAttribute(END_LON, startEndState.endLon);
//        startEndElement.setAttribute(STEPS, startEndState.steps);
//        startEndElement.setAttribute(TIME_INTERVAL, startEndState.timeBetweenSteps);

        // add point list panel data
//        Element pointListElement = new Element(POINT_LIST_PANEL_TAG);
//        pointListElement.setAttribute(TIME_INTERVAL, pointListState.timeInterval);
//        pointListElement.setAttribute(LOOP, String.valueOf(pointListState.loop));
//        pointListElement.setAttribute(REVERSE, String.valueOf(pointListState.reverse));
//
//        Element pointsListElement = new Element(POINT_LIST_TAG);
//        for(int i = 0; i < pointListState.getRowCount(); i++){
//            Element pointElement = new Element(POINT_TAG);
//            pointElement.setAttribute("lat", pointListState.getLatStringAt(i));
//            pointElement.setAttribute("lon", pointListState.getLonStringAt(i));
//            pointListElement.addContent(pointElement);
//        }
//
//        element.addContent(pointsListElement);


        return element;
    }

    @Override
    public void loadState(Element state) {
        System.out.println("loading settings state");
        LOG.debug("loading settings state");
        pointListState = new PointListEmulationPanel.State();
        startEndState = new StartEndEmulationPanel.State();
        toolWindowState = new MainToolWindow.State();

        final Element toolWindowElement = state.getChild(TOOL_WINDOW_TAG);
        toolWindowState.restore(toolWindowElement);
//        toolWindowState.port = toolWindowElement.getAttributeValue(PORT);
//        toolWindowState.selectedPanel = Integer.valueOf(toolWindowElement.getAttributeValue(SELECTED_PANEL));

        final Element startEndElement = state.getChild(START_END_PANEL_TAG);
        startEndState.restore(startEndElement);
//        startEndState.startLat = startEndElement.getAttributeValue(START_LAT);
//        startEndState.startLon = startEndElement.getAttributeValue(START_LON);
//        startEndState.endLat = startEndElement.getAttributeValue(END_LAT);
//        startEndState.endLon = startEndElement.getAttributeValue(END_LON);
//        startEndState.steps = startEndElement.getAttributeValue(STEPS);
//        startEndState.timeBetweenSteps = startEndElement.getAttributeValue(TIME_INTERVAL);

        final Element pointListPanelElement = state.getChild(POINT_LIST_PANEL_TAG);
        pointListState.restore(pointListPanelElement);
//        pointListState.timeInterval = pointListPanelElement.getAttributeValue(TIME_INTERVAL);
//        pointListState.loop = Boolean.valueOf(pointListPanelElement.getAttributeValue(LOOP));
//        pointListState.reverse = Boolean.valueOf(pointListPanelElement.getAttributeValue(REVERSE));
//
//        Element pointsListElement = pointListPanelElement.getChild(POINT_LIST_TAG);
//        List<Element> children = pointsListElement.getChildren();
//        for(Element child : children){
//            pointListState.addRow(child.getAttributeValue("lat"), child.getAttributeValue("lon"));
//        }

    }

    public void setToolWindowPresenter(ToolWindowPresenter toolWindowPresenter) {
        this.toolWindowPresenter = toolWindowPresenter;
    }

    public ToolWindowPresenter getToolWindowPresenter() {
        return toolWindowPresenter;
    }
}
