package ca.team4152.autoeditor.utils.menu;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileIO {

    public static String getSaveDirectory(){
        return getFileDirectory("Save");
    }

    public static String getOpenDirectory(){
        return getFileDirectory("Open");
    }

    public static String getFileDirectory(String approveButtonText){
        String value = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));

        int option = fileChooser.showDialog(null, approveButtonText);
        if(option == JFileChooser.APPROVE_OPTION)
            value = fileChooser.getCurrentDirectory().getAbsolutePath() + "\\" + fileChooser.getSelectedFile().getName();
        if(!value.isEmpty() && !value.contains(".txt"))
            value += ".txt";

        return value;
    }

}