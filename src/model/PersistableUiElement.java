package model;

/**
 * Created by Thomas on 1/1/2016.
 */
public interface PersistableUiElement<T extends PersistableState> {

    T getState();
    void restoreState(T state);
    String getElementName();

}
