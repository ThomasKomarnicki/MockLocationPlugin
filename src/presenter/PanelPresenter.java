package presenter;

import com.intellij.openapi.components.ServiceManager;
import model.GpsEmulationModel;
import model.event.EmulationStartedEvent;
import model.event.EmulationStoppedEvent;
import service.EmulationService;
import service.ProgressCallback;
import ui.EmulationPanel;
import ui.MainToolWindow;

/**
 * Created by Thomas on 12/22/2015.
 */
public class PanelPresenter implements ProgressCallback{

    private EmulationPanel currentEmulationPanel;
    private EmulationService emulationService;
    private MainToolWindow mainToolWindow;

    public PanelPresenter(MainToolWindow mainToolWindow){
        this.mainToolWindow = mainToolWindow;
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
            mainToolWindow.onGpsEmulationStopped(new EmulationStoppedEvent());
        }else{
            if(currentEmulationPanel.validateData()){
                GpsEmulationModel gpsEmulationModel = currentEmulationPanel.createGpsEmulationData();
                if(gpsEmulationModel != null) {
                    int port = mainToolWindow.getPort();
                    if(port > 0) {
                        emulationService.startEmulation(gpsEmulationModel, port);
                        mainToolWindow.onGpsEmulationStarted(new EmulationStartedEvent());
                    }
                }

            }

        }
    }


    @Override
    public void onProgressEvent(int progress) {
        mainToolWindow.onProgressEvent(progress);
    }
}
