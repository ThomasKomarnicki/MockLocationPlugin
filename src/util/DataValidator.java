package util;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Created by Thomas on 12/22/2015.
 */
public class DataValidator {

    public DataValidator(){

    }

    /**
     * validates input and puts errors into errorText TextField
     * @return if any erros were found
     */
    private boolean validateInput(){
        String errors = "";
        boolean valid = true;

        String validateStartingLatResult = validateLat(startLocationLat.getText(), "Start Latitude");
        if(validateStartingLatResult != null){
            valid = false;
            errors += validateStartingLatResult;
        }

        String validateStartingLonResult = validateLon(startLocationLon.getText(), "Start Longitude");
        if(validateStartingLonResult != null){
            valid = false;
            errors += validateStartingLonResult;
        }

        String validateEndLatResult = validateLat(endLocationLat.getText(), "End Latitude");
        if(validateEndLatResult != null){
            valid = false;
            errors += validateEndLatResult;
        }

        String validateEndLonResult = validateLon(endLocationLon.getText(), "End Longitude");
        if(validateEndLonResult != null){
            valid = false;
            errors += validateEndLonResult;
        }

        try{
            Integer.valueOf(stepsTextField.getText());
        } catch (Exception e){
            valid = false;
            errors += "Steps must be a valid Integer<br>";
        }

        try{
            Integer.valueOf(timeIntervalField.getText());
        } catch (Exception e){
            valid = false;
            errors += "Time Interval must be a valid Integer<br>";
        }

        if(!valid){
            errorText.setText("<html>"+errors+"</html>");

        }

        return valid;
    }

    /**
     *
     * @param lat
     * @param latName
     * @return error string if the entered latitude is not valid
     */
    private String validateLat(String lat, String latName){
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
    private String validateLon(String lon, String lonName){
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
}
