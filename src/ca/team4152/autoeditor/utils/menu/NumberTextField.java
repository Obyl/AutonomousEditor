package ca.team4152.autoeditor.utils.menu;

import javax.swing.JFormattedTextField;

public class NumberTextField extends JFormattedTextField{

    public NumberTextField(){
        setFormatter(new NumberTextFieldFormatter());
    }

    public void setCeiling(int maximum){
        if(maximum < 0)
            maximum = 0;

        ((NumberTextFieldFormatter) getFormatter()).setCeiling(maximum);
    }
}