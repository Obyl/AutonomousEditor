package ca.team4152.autoeditor.utils.menu;

import javax.swing.JFormattedTextField;

public class NumberTextField extends JFormattedTextField{

    public NumberTextField(){
        setFormatter(new NumberTextFieldFormatter());
    }
}