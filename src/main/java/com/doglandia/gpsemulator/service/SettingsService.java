package com.doglandia.gpsemulator.service;

import com.doglandia.gpsemulator.model.PersistableState;
import com.doglandia.gpsemulator.presenter.ToolWindowPresenterInterface;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.diagnostic.Logger;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;

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


    private final  Logger LOG = Logger.getInstance(SettingsService.class);
    private ToolWindowPresenterInterface toolWindowPresenter;

    private Element lastState;

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


        return element;
    }

    @Override
    public void loadState(Element state) {
        System.out.println("loading settings state");
        LOG.debug("loading settings state");
        if(toolWindowPresenter != null){
            toolWindowPresenter.loadPersistableStates(state);
        }else{
            lastState = state;
        }

    }

    public void setToolWindowPresenter(ToolWindowPresenterInterface toolWindowPresenter) {
        this.toolWindowPresenter = toolWindowPresenter;
        if(lastState != null){
            toolWindowPresenter.loadPersistableStates(lastState);
        }
    }
}
