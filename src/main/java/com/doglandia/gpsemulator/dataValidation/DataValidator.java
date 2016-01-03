package com.doglandia.gpsemulator.dataValidation;

import org.apache.commons.lang.math.NumberUtils;
import org.jetbrains.annotations.NotNull;

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

    public DataValidator validateLat(@NotNull String lat, String latName){
        String error = validateLatString(lat.trim(), latName);
        if(error != null){
            valid = false;
            errorsText.append("<br>" + error);
        }
        return this;
    }

    public DataValidator validateLon(@NotNull String lon, String lonName){
        String error = validateLonString(lon.trim(), lonName);
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

    public boolean isCurrentlyValid(){
        return valid;
    }

    public void addErrorMessage(String errorMessage){
        errorsText.append("<br>"+errorMessage);
    }

    public ValidationResult getResult(){
        return new ValidationResult(valid, errorsText.toString());
    }

    /**
     *
     * @param lat
     * @param latName
     * @return error string if the entered latitude is not valid
     */
    public static String validateLatString(String lat, String latName){
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
    public static String validateLonString(String lon, String lonName){
        if(NumberUtils.isNumber(lon)){
            double startLat = Double.valueOf(lon);
            if(startLat < -180 || startLat > 180){
                return lonName + " must be a valid number between -180 and 180<br>";
            }
        }else{
            return lonName + " must be a valid number between -180 and 180<br>";
        }
        return null;
    }

    public static String validateIntString(String intString, String intName){
        try{
            Integer.valueOf(intString);
        } catch (Exception e){
            return  intName + " must be a valid Integer<br>";
        }
        return null;
    }

}
