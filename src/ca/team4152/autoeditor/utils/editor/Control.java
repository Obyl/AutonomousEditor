package ca.team4152.autoeditor.utils.editor;

import ca.team4152.autoeditor.Editor;

import javax.swing.JOptionPane;

public class Control {

    private static final int CANCEL = 0;

    private static Field storedField = null;
    private static RobotPath storedPath = null;

    public static void newField(){
        if(savePrompt(true) == CANCEL)
            return;
    }

    public static void newPath(){
        if(savePrompt(false) == CANCEL)
            return;
    }

    public static void openField(){
        if(savePrompt(true) == CANCEL)
            return;
    }

    public static void openPath(){
        if(savePrompt(false) == CANCEL)
            return;
    }

    public static void saveField(){

    }

    public static void savePath(){

    }

    private static int savePrompt(boolean field){
        String[] options = {"Cancel", "No", "Yes"};
        final int cancel = 0;
        final int yes = 2;

        //Check if the path should be saved if we're manipulating either the field or the path.
        if(storedPath == null || !storedPath.equals(Editor.getCurrentPath())){
            switch (JOptionPane.showOptionDialog(null, "Would you like to save the path before continuing?",
                    "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2])){
                case cancel:
                    return CANCEL;
                case yes:
                    savePath();
                    break;
            }
        }
        //Check if the field should be saved if we're only manipulating the field.
        if((storedField == null || !storedField.equals(Editor.getCurrentField())) && field){
            switch (JOptionPane.showOptionDialog(null, "Would you like to save the field before continuing?",
                    "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2])){
                case cancel:
                    return CANCEL;
                case yes:
                    saveField();
                    break;
            }
        }

        return 1;
    }

}