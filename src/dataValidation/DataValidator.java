package dataValidation;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Created by Thomas on 12/22/2015.
 */
public class DataValidator {

    private boolean valid = true;
    private StringBuilder errorsText;

    public DataValidator(){

    }

    public DataValidator startValidation(){

        valid = true;
        errorsText = new StringBuilder();

        return this;
    }

    public DataValidator validateLat(String lat, String latName){
        String error = validateLatString(lat, latName);
        if(error != null){
            valid = false;
            errorsText.append("<br>" + error);
        }
        return this;
    }

    public DataValidator validateLon(String lon, String lonName){
        String error = validateLonString(lon, lonName);
        if(error != null){
            valid = false;
            errorsText.append("<br>" + error);
        }
        return this;
    }

    public DataValidator validateInt(String intString, String intName){
        String error = validateIntString(intString, intName);
        if(error != null){
            valid = false;
            errorsText.append("<br>" + error);
        }
        return this;
    }

    public ValidationResult getResult(){
        return new ValidationResult(valid, errorsText.toString());
    }

//    private boolean validateInput(){
//        String errors = "";
//        boolean valid = true;
//
//        String validateStartingLatResult = validateLat(startLocationLat.getText(), "Start Latitude");
//        if(validateStartingLatResult != null){
//            valid = false;
//            errors += validateStartingLatResult;
//        }
//
//        String validateStartingLonResult = validateLon(startLocationLon.getText(), "Start Longitude");
//        if(validateStartingLonResult != null){
//            valid = false;
//            errors += validateStartingLonResult;
//        }
//
//        String validateEndLatResult = validateLat(endLocationLat.getText(), "End Latitude");
//        if(validateEndLatResult != null){
//            valid = false;
//            errors += validateEndLatResult;
//        }
//
//        String validateEndLonResult = validateLon(endLocationLon.getText(), "End Longitude");
//        if(validateEndLonResult != null){
//            valid = false;
//            errors += validateEndLonResult;
//        }
//
//        try{
//            Integer.valueOf(stepsTextField.getText());
//        } catch (Exception e){
//            valid = false;
//            errors += "Steps must be a valid Integer<br>";
//        }
//
//        try{
//            Integer.valueOf(timeIntervalField.getText());
//        } catch (Exception e){
//            valid = false;
//            errors += "Time Interval must be a valid Integer<br>";
//        }
//
//        if(!valid){
//            errorText.setText("<html>"+errors+"</html>");
//
//        }
//
//        return valid;
//    }

    /**
     *
     * @param lat
     * @param latName
     * @return error string if the entered latitude is not valid
     */
    private String validateLatString(String lat, String latName){
        if(NumberUtils.isNumber(lat)){
            double startLat = Double.valueOf(lat);
            if(startLat < -90 || startLat > 90){
                return latName + " must be a valid number between -90 and 90<br>";
            }
        }else{
            return latName + " must be a valid number between -90 and 90<br>";
        }
        return null;
    }

    /**
     *
     * @param lon
     * @param lonName
     * @return error string is enerted longitude is not valid
     */
    private String validateLonString(String lon, String lonName){
        if(NumberUtils.isNumber(lon)){
            double startLat = Double.valueOf(lon);
            if(startLat < -90 || startLat > 90){
                return lonName + " must be a valid number between -180 and 180<br>";
            }
        }else{
            return lonName + " must be a valid number between -180 and 180<br>";
        }
        return null;
    }

    private String validateIntString(String intString, String intName){
        try{
            Integer.valueOf(intString);
        } catch (Exception e){
            return  intName + " must be a valid Integer<br>";
        }
        return null;
    }

}
