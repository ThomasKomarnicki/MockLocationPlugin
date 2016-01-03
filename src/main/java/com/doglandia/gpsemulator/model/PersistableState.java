package com.doglandia.gpsemulator.model;

import org.jdom.Element;

/**
 * Created by Thomas on 1/1/2016.
 */
public interface PersistableState {

    Element save();
    void restore(Element element);
}
