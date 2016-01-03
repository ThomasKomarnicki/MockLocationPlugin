package com.doglandia.gpsemulator.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by Thomas on 1/2/2016.
 */
public class EmulatorErrorDialog extends DialogWrapper {


    private JPanel emulatorErrorDialogPanel;
    private JLabel errorMessage;

    public EmulatorErrorDialog(@Nullable Project project, boolean canBeParent, String error) {
        super(project, canBeParent);
        init();
        setTitle("Android Emulator Error");

        errorMessage.setText(error);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return emulatorErrorDialogPanel;
    }
}
