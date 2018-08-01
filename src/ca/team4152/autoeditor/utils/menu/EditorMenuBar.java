package ca.team4152.autoeditor.utils.menu;

import javax.swing.JMenuBar;

public class EditorMenuBar extends JMenuBar{

    public EditorMenuBar(){
        add(new MenuFile());
        add(new MenuEdit());
    }

}