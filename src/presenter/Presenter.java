package presenter;

import com.google.common.eventbus.Subscribe;
import com.intellij.openapi.components.ServiceManager;
import model.GpsEmulationModel;
import service.EmulationService;
import service.ProgressCallback;
import ui.EmulationPanel;

/**
 * Created by Thomas on 12/22/2015.
 */
public class Presenter implements ProgressCallback{

    private EmulationPanel currentEmulationPanel;
    private EmulationService emulationService;

    public Presenter(){
        EmulationService.getBus().register(this);
        emulationService = ServiceManager.getService(EmulationService.class);
        emulationService.setProgressCallback(this);
    }

    public boolean emulationRunning() {
        return emulationService.isRunning();
    }

    public void setCurrentEmulationPanel(EmulationPanel currentEmulationPanel) {
        this.currentEmulationPanel = currentEmulationPanel;
    }

    public void onEmulationButtonClick(){
        if(emulationRunning()){
            emulationService.stopCurrentEmulation();
            currentEmulationPanel.onGpsEmulationStopped();
        }else{
            if(currentEmulationPanel.validateData()){
                GpsEmulationModel gpsEmulationModel = currentEmulationPanel.createGpsEmulationData();
                emulationService.startEmulation(gpsEmulationModel);
                currentEmulationPanel.onGpsEmulationStarted();

            }

        }
    }


    @Override
    public void onProgressEvent(int progress) {

    }
}
