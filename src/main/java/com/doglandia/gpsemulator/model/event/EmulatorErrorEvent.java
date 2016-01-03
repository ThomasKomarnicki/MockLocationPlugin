package com.doglandia.gpsemulator.model.event;

/**
 * Created by Thomas on 1/2/2016.
 */
public class EmulatorErrorEvent {

    public EmulatorErrorEvent(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String errorMessage;

    public String getMessage() {
        return errorMessage;
    }
}
