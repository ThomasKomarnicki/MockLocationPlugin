package service;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;
import ui.MainToolWindow;
import ui.PointListEmulationPanel;
import ui.StartEndEmulationPanel;

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
    private static final String PORT = "port";
    private static final String SELECTED_PANEL = "panel";

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

    /**
     * constructs xml tree representing the state of the tool window and its components
     * @return
     */
    @Nullable
    @Override
    public Element getState() {
        final Element element = new Element(SETTINGS_TAG);

        // add tool window data
        final Element toolWindowElement = new Element(TOOL_WINDOW_TAG);
        element.setAttribute(PORT,toolWindowState.port);
        element.setAttribute(SELECTED_PANEL, String.valueOf(toolWindowState.selectedPanel));
        element.addContent(toolWindowElement);

        // add start end panel data
        Element startEndElement = new Element(START_END_PANEL_TAG);
        startEndElement.setAttribute(START_LAT, startEndState.startLat);
        startEndElement.setAttribute(START_LON, startEndState.startLon);
        startEndElement.setAttribute(END_LAT, startEndState.endLat);
        startEndElement.setAttribute(END_LON, startEndState.endLon);
        startEndElement.setAttribute(STEPS, startEndState.steps);
        startEndElement.setAttribute(TIME_INTERVAL, startEndState.timeBetweenSteps);

        // add point list panel data
        Element pointListElement = new Element(POINT_LIST_PANEL_TAG);
        pointListElement.setAttribute(TIME_INTERVAL, pointListState.timeInterval);
        pointListElement.setAttribute(LOOP, String.valueOf(pointListState.loop));
        pointListElement.setAttribute(REVERSE, String.valueOf(pointListState.reverse));

        Element pointsListElement = new Element(POINT_LIST_TAG);
        for(int i = 0; i < pointListState.getRowCount(); i++){
            Element pointElement = new Element(POINT_TAG);
            pointElement.setAttribute("lat", pointListState.getLatStringAt(i));
            pointElement.setAttribute("lon", pointListState.getLonStringAt(i));
            pointListElement.addContent(pointElement);
        }


        return element;
    }

    @Override
    public void loadState(Element state) {

    }
}
