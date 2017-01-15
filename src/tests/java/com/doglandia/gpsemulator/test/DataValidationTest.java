package com.doglandia.gpsemulator.test;

import com.doglandia.gpsemulator.dataValidation.DataValidator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Thomas on 1/2/2016.
 */
public class DataValidationTest {

    @Test
    public void testEmulation(){
        DataValidator dataValidator = new DataValidator();


        // lat / long ranges test
        dataValidator.startValidation();
        dataValidator.validateLon("181","");
        assertFalse(dataValidator.getResult().isValid());

        dataValidator.startValidation();
        dataValidator.validateLon("-181","");
        assertFalse(dataValidator.getResult().isValid());

        dataValidator.startValidation();
        dataValidator.validateLon("100","");
        assertTrue(dataValidator.getResult().isValid());


        dataValidator.startValidation();
        dataValidator.validateLat("91","");
        assertFalse(dataValidator.getResult().isValid());

        dataValidator.startValidation();
        dataValidator.validateLat("-91","");
        assertFalse(dataValidator.getResult().isValid());

        dataValidator.startValidation();
        dataValidator.validateLat("45","");
        assertTrue(dataValidator.getResult().isValid());

        // invalid number test
        dataValidator.startValidation();
        dataValidator.validateLat("dog","");
        assertFalse(dataValidator.getResult().isValid());

        dataValidator.startValidation();
        dataValidator.validateLon("dog","");
        assertFalse(dataValidator.getResult().isValid());

        dataValidator.startValidation();
        dataValidator.validateInt("dog","");
        assertFalse(dataValidator.getResult().isValid());

        dataValidator.startValidation();
        dataValidator.validateLat("0.0.1","");
        assertFalse(dataValidator.getResult().isValid());
    }
}
