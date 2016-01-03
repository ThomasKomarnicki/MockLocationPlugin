package com.doglandia.gpsemulator;

import javax.swing.*;
import java.lang.reflect.Field;

/**
 * Created by Thomas on 1/2/2016.
 */
public class TestUtils {

    public static void setTextOfField(Object objectWithTextField, String textFieldName, String text){
        try {
            Field field = objectWithTextField.getClass().getDeclaredField(textFieldName);
            field.setAccessible(true);
            JTextField jTextField = (JTextField) field.get(objectWithTextField);
            jTextField.setText(text);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setFieldSelection(Object objectWithTextField, String textFieldName, boolean selection){
        try {
            Field field = objectWithTextField.getClass().getDeclaredField(textFieldName);
            field.setAccessible(true);
            JCheckBox jCheckBox = (JCheckBox) field.get(objectWithTextField);
            jCheckBox.setSelected(selection);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setFieldValue(Object objectWithField, String textFieldName, Object object){
        try {
            Field field = objectWithField.getClass().getDeclaredField(textFieldName);
            field.setAccessible(true);
            field.set(objectWithField, object);
//            JTextField jTextField = (JTextField) field.get(objectWithField);
//            jTextField.setText(text);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
