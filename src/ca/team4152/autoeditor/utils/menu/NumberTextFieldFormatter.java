package ca.team4152.autoeditor.utils.menu;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.text.ParseException;

public class NumberTextFieldFormatter extends NumberFormatter{

    public NumberTextFieldFormatter(){
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        setFormat(format);

        setValueClass(Integer.class);
        setAllowsInvalid(false);
        setMinimum(0);
    }

    @Override
    public Object stringToValue(String text) throws ParseException{
        if(text.isEmpty())
            return null;
        else
            return super.stringToValue(text);
    }
}