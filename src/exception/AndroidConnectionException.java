package exception;

/**
 * Created by Thomas on 1/2/2016.
 */
public class AndroidConnectionException extends Exception {

    public AndroidConnectionException(){
        super("Could not connect to an Android emulator. Make sure an emulator is running on the specified port");
    }
}
