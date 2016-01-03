package com.doglandia.gpsemulator.presenter;

import com.doglandia.gpsemulator.model.PersistableState;
import org.jdom.Element;

import java.util.List;

/**
 * Created by Thomas on 1/2/2016.
 */
public interface ToolWindowPresenterInterface {

    List<PersistableState> getPersistableStates();

    void loadPersistableStates(Element rootElement);
}
