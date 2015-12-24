package dataValidation;

/**
 * Created by Thomas on 12/23/2015.
 */
public class ValidationResult {

    private boolean valid;
    private String errorsText;

    public ValidationResult(boolean valid, String errorsText){
        this.valid = valid;
        this.errorsText = errorsText;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorsText() {
        return errorsText;
    }
}
