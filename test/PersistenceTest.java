import dataValidation.DataValidator;
import model.PersistableState;
import org.jdom.Element;
import org.junit.Test;
import presenter.ToolWindowPresenter;
import service.SettingsService;
import ui.MainToolWindow;

import java.util.List;

/**
 * Created by Thomas on 1/2/2016.
 */
public class PersistenceTest {

    private static class TestToolWindowPresenter extends ToolWindowPresenter{

        public TestToolWindowPresenter() {
            super(null);
        }

        @Override
        public List<PersistableState> getPersistableStates() {
            return super.getPersistableStates();
        }

        @Override
        public void loadPersistableStates(Element rootElement) {
            super.loadPersistableStates(rootElement);
        }
    }

    @Test
    public void testUiPersistence(){
        SettingsService settingsService = new SettingsService();
        ToolWindowPresenter toolWindowPresenter = new TestToolWindowPresenter()
        MainToolWindow mainToolWindow = new MainToolWindow();

    }
}